package com.lkountoura.chemiq.viewmodel.helpers

import com.lkountoura.chemiq.data.dto.QuestionDTO
import kotlin.random.Random

data class QuizAttempt(
    val questions: List<QuestionDTO>,
    val currentIndex: Int = 0,
    val selectedOptions: List<Int?> = List(questions.size) { null },
) {
    val currentQuestion: QuestionDTO
        get() = questions[currentIndex]

    val currentSelection: Int?
        get() = selectedOptions[currentIndex]

    val isLast: Boolean
        get() = currentIndex == questions.lastIndex

    val isFinished: Boolean
        get() = isLast && currentSelection != null

    val correctCount: Int
        get() = questions.indices.count { isCorrect(it) == true }

    val bestStreak: Int
        get() = questions.indices
            .runningFold(0) { streak, index -> if (isCorrect(index) == true) streak + 1 else 0 }
            .max()

    fun isCorrect(index: Int): Boolean? =
        selectedOptions[index]?.let { option -> option == questions[index].correct }

    fun answer(option: Int): QuizAttempt {
        if (currentSelection != null) return this
        return copy(
            selectedOptions = selectedOptions.toMutableList().also { it[currentIndex] = option }
        )
    }

    fun next(): QuizAttempt {
        if (currentSelection == null || isLast) return this
        return copy(currentIndex = currentIndex + 1)
    }

    companion object {
        fun draw(
            pool: List<QuestionDTO>,
            size: Int,
            random: Random = Random.Default,
        ): QuizAttempt = QuizAttempt(
            questions = pool.distinctBy { it.id }.shuffled(random).take(size)
        )

        fun restore(
            pool: List<QuestionDTO>,
            questionIds: IntArray?,
            currentIndex: Int?,
            selectedOptions: IntArray?,
        ): QuizAttempt? {
            if (questionIds == null || currentIndex == null || selectedOptions == null) return null
            if (questionIds.isEmpty() || selectedOptions.size != questionIds.size) return null
            if (currentIndex !in questionIds.indices) return null

            val poolById = pool.associateBy { it.id }
            val questions = questionIds.map { id -> poolById[id] ?: return null }

            return QuizAttempt(
                questions = questions,
                currentIndex = currentIndex,
                selectedOptions = selectedOptions.map { option -> option.takeIf { it != NO_OPTION } }
            )
        }

        const val NO_OPTION = 0
    }
}