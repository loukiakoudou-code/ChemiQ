package com.lkountoura.chemiq.viewmodel.helpers

import com.lkountoura.chemiq.data.dto.QuestionDTO
import com.lkountoura.chemiq.xp.XpRules
import kotlin.random.Random

data class TimeAttackRun(
    val questions: List<QuestionDTO>,
    val currentIndex: Int = 0,
    val selectedOption: Int? = null,
    val remainingSeconds: Int = TOTAL_SECONDS,
    val correctCount: Int = 0,
    val answeredCount: Int = 0,
    val streak: Int = 0,
    val bestStreak: Int = 0,
    val xpEarned: Int = 0,
) {
    val currentQuestion: QuestionDTO
        get() = questions[currentIndex]

    val isAnswered: Boolean
        get() = selectedOption != null

    val isOver: Boolean
        get() = remainingSeconds <= 0


    fun tick(): TimeAttackRun =
        if (isOver) this else copy(remainingSeconds = remainingSeconds - 1)



    fun answer(option: Int): TimeAttackRun {
        if (isAnswered || isOver) return this

        val isCorrect = option == currentQuestion.correct
        val newStreak = if (isCorrect) streak + 1 else 0

        return copy(
            selectedOption = option,
            answeredCount = answeredCount + 1,
            correctCount = if (isCorrect) correctCount + 1 else correctCount,
            streak = newStreak,
            bestStreak = maxOf(bestStreak, newStreak),
            xpEarned = if (isCorrect) xpEarned + XpRules.timeAttackCorrect(newStreak) else xpEarned
        )
    }


    fun next(random: Random = Random.Default): TimeAttackRun {
        if (!isAnswered) return this

        val nextIndex = currentIndex + 1
        return if (nextIndex < questions.size) {
            copy(currentIndex = nextIndex, selectedOption = null)
        } else {
            copy(questions = questions.shuffled(random), currentIndex = 0, selectedOption = null)
        }
    }

    companion object {
        const val TOTAL_SECONDS = 60

        fun start(pool: List<QuestionDTO>, random: Random = Random.Default): TimeAttackRun =
            TimeAttackRun(questions = pool.distinctBy { it.id }.shuffled(random))
    }
}