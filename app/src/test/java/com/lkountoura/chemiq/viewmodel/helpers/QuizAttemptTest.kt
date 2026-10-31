package com.lkountoura.chemiq.viewmodel.helpers

import com.lkountoura.chemiq.data.dto.QuestionDTO
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class QuizAttemptTest {

    private fun question(id: Int, correct: Int = 2) = QuestionDTO(
        id = id,
        unitId = 1,
        difficulty = 1,
        text = "Ερώτηση $id",
        option1 = "Α$id",
        option2 = "Β$id",
        option3 = "Γ$id",
        option4 = "Δ$id",
        correct = correct,
        explanation = "Εξήγηση $id"
    )

    private fun pool(size: Int): List<QuestionDTO> = (1..size).map { question(id = 100 + it) }

    @Test
    fun draw_containsEveryPoolQuestionExactlyOnce() {
        val pool = pool(10)

        val attempt = QuizAttempt.draw(pool = pool, size = 10, random = Random(1))

        assertEquals(10, attempt.questions.size)
        assertEquals(pool.map { it.id }.toSet(), attempt.questions.map { it.id }.toSet())
    }

    @Test
    fun draw_isDeterministicForTheSameSeed() {
        val pool = pool(10)

        val first = QuizAttempt.draw(pool = pool, size = 10, random = Random(42))
        val second = QuizAttempt.draw(pool = pool, size = 10, random = Random(42))

        assertEquals(first.questions.map { it.id }, second.questions.map { it.id })
    }

    @Test
    fun draw_doesNotKeepTheStoredOrder() {
        val pool = pool(10)
        val storedOrder = pool.map { it.id }

        val orders = (0 until 20).map { seed ->
            QuizAttempt.draw(pool = pool, size = 10, random = Random(seed)).questions.map { it.id }
        }

        assertTrue(orders.any { it != storedOrder })
    }

    @Test
    fun draw_limitsALargerPoolToTheRequestedSizeWithoutRepeats() {
        val attempt = QuizAttempt.draw(pool = pool(15), size = 10, random = Random(7))

        assertEquals(10, attempt.questions.size)
        assertEquals(10, attempt.questions.map { it.id }.toSet().size)
    }

    @Test
    fun draw_usesTheWholePoolWhenItIsSmallerThanTheRequestedSize() {
        val attempt = QuizAttempt.draw(pool = pool(3), size = 10)

        assertEquals(3, attempt.questions.size)
    }

    @Test
    fun draw_returnsNoQuestionsForAnEmptyPool() {
        val attempt = QuizAttempt.draw(pool = emptyList(), size = 10)

        assertTrue(attempt.questions.isEmpty())
    }

    @Test
    fun draw_keepsOneRowPerQuestionId() {
        val poolWithStaleRow = pool(5) + question(id = 101).copy(text = "Παλιό κείμενο")

        val attempt = QuizAttempt.draw(pool = poolWithStaleRow, size = 10, random = Random(3))

        assertEquals(5, attempt.questions.size)
        assertEquals(5, attempt.questions.map { it.id }.toSet().size)
    }

    @Test
    fun answer_recordsTheFirstSelection() {
        val attempt = QuizAttempt(questions = pool(3)).answer(option = 3)

        assertEquals(3, attempt.currentSelection)
    }

    @Test
    fun answer_isIgnoredOnceTheQuestionIsAnswered() {
        val answered = QuizAttempt(questions = pool(3)).answer(option = 3)

        val answeredAgain = answered.answer(option = 1)

        assertSame(answered, answeredAgain)
        assertEquals(3, answeredAgain.currentSelection)
    }

    @Test
    fun isCorrect_comparesTheSelectionWithTheCorrectOption() {
        val questions = listOf(question(id = 1, correct = 2), question(id = 2, correct = 4))

        val attempt = QuizAttempt(questions = questions).answer(option = 2).next().answer(option = 1)

        assertEquals(true, attempt.isCorrect(0))
        assertEquals(false, attempt.isCorrect(1))
    }

    @Test
    fun isCorrect_isNullForAnUnansweredQuestion() {
        assertNull(QuizAttempt(questions = pool(2)).isCorrect(0))
    }

    @Test
    fun next_isIgnoredWhileTheQuestionIsUnanswered() {
        val attempt = QuizAttempt(questions = pool(3))

        assertSame(attempt, attempt.next())
    }

    @Test
    fun next_movesToAnUnansweredFollowingQuestion() {
        val attempt = QuizAttempt(questions = pool(3)).answer(option = 1).next()

        assertEquals(1, attempt.currentIndex)
        assertNull(attempt.currentSelection)
    }

    @Test
    fun next_staysOnTheLastQuestion() {
        val lastAnswered = QuizAttempt(questions = pool(1)).answer(option = 1)

        assertTrue(lastAnswered.isLast)
        assertSame(lastAnswered, lastAnswered.next())
    }

    @Test
    fun restore_rebuildsTheSavedAttempt() {
        val pool = pool(4)
        val original = QuizAttempt.draw(pool = pool, size = 4, random = Random(5))
            .answer(option = 2)
            .next()

        val restored = QuizAttempt.restore(
            pool = pool,
            questionIds = original.questions.map { it.id }.toIntArray(),
            currentIndex = original.currentIndex,
            selectedOptions = original.selectedOptions.map { it ?: QuizAttempt.NO_OPTION }.toIntArray()
        )

        assertEquals(original, restored)
    }

    @Test
    fun restore_returnsNullWhenASavedQuestionIsNoLongerInThePool() {
        val restored = QuizAttempt.restore(
            pool = pool(3),
            questionIds = intArrayOf(101, 102, 999),
            currentIndex = 0,
            selectedOptions = intArrayOf(0, 0, 0)
        )

        assertNull(restored)
    }

    @Test
    fun restore_returnsNullWithoutSavedState() {
        val restored = QuizAttempt.restore(
            pool = pool(3),
            questionIds = null,
            currentIndex = null,
            selectedOptions = null
        )

        assertNull(restored)
    }

    private fun answered(questions: List<QuestionDTO>, options: List<Int>): QuizAttempt =
        options.foldIndexed(QuizAttempt(questions = questions)) { index, attempt, option ->
            val answeredAttempt = attempt.answer(option)
            if (index < options.lastIndex) answeredAttempt.next() else answeredAttempt
        }

    @Test
    fun isFinished_onlyOnceTheLastQuestionIsAnswered() {
        val questions = pool(2)

        assertFalse(answered(questions, options = listOf(1)).isFinished)
        assertFalse(answered(questions, options = listOf(1)).next().isFinished)
        assertTrue(answered(questions, options = listOf(1, 1)).isFinished)
    }

    @Test
    fun correctCount_countsOnlyCorrectAnswers() {
        val questions = (1..3).map { question(id = it, correct = 1) }

        val attempt = answered(questions, options = listOf(1, 4, 1))

        assertEquals(2, attempt.correctCount)
    }

    @Test
    fun bestStreak_isTheLongestRunOfCorrectAnswers() {
        val questions = (1..6).map { question(id = it, correct = 1) }

        val attempt = answered(questions, options = listOf(1, 1, 2, 1, 1, 1))

        assertEquals(3, attempt.bestStreak)
    }

    @Test
    fun bestStreak_isZeroWithoutCorrectAnswers() {
        assertEquals(0, QuizAttempt(questions = pool(3)).bestStreak)
    }
}
