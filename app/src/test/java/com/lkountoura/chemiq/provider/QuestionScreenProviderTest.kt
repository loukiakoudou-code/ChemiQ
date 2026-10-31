package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.data.dto.QuestionDTO
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionStatus
import com.lkountoura.chemiq.ui.component.questionScreenPage.SegmentStatus
import com.lkountoura.chemiq.viewmodel.helpers.QuizAttempt
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestionScreenProviderTest {

    private val provider = QuestionScreenProvider()

    private fun question(id: Int, correct: Int) = QuestionDTO(
        id = id,
        unitId = 1,
        difficulty = 1,
        text = "Ερώτηση $id",
        option1 = "Επιλογή 1",
        option2 = "Επιλογή 2",
        option3 = "Επιλογή 3",
        option4 = "Επιλογή 4",
        correct = correct,
        explanation = "Εξήγηση $id"
    )

    private val questions = listOf(
        question(id = 1, correct = 2),
        question(id = 2, correct = 4),
        question(id = 3, correct = 1),
    )

    private fun answerStatuses(attempt: QuizAttempt): List<QuestionStatus> =
        provider.provideData(attempt).questionPanelState.questionTab.map { it.status }

    private fun segmentStatuses(attempt: QuizAttempt): List<SegmentStatus> =
        provider.provideData(attempt).questionProgressPanelState.unitSegment.map { it.status }

    @Test
    fun unansweredQuestion_isInteractiveAndHidesFeedback() {
        val state = provider.provideData(QuizAttempt(questions = questions))

        assertEquals(List(4) { QuestionStatus.DEFAULT }, state.questionPanelState.questionTab.map { it.status })
        assertFalse(state.questionPanelState.isLocked)
        assertNull(state.explanationPanelState)
        assertNull(state.buttonState)
    }

    @Test
    fun answers_useGreekLettersAndOptionNumbersAsTags() {
        val tabs = provider.provideData(QuizAttempt(questions = questions)).questionPanelState.questionTab

        assertEquals(listOf("Α", "Β", "Γ", "Δ"), tabs.map { it.letter })
        assertEquals(listOf("1", "2", "3", "4"), tabs.map { it.tag })
        assertEquals(listOf("Επιλογή 1", "Επιλογή 2", "Επιλογή 3", "Επιλογή 4"), tabs.map { it.text })
    }

    @Test
    fun correctAnswer_isMarkedCorrectAndTheRestAreDimmed() {
        val attempt = QuizAttempt(questions = questions).answer(option = 2)

        assertEquals(
            listOf(QuestionStatus.DIMMED, QuestionStatus.CORRECT, QuestionStatus.DIMMED, QuestionStatus.DIMMED),
            answerStatuses(attempt)
        )
        assertTrue(provider.provideData(attempt).questionPanelState.isLocked)
    }

    @Test
    fun wrongAnswer_isMarkedWrongAndTheCorrectOneIsRevealed() {
        val attempt = QuizAttempt(questions = questions).answer(option = 3)

        assertEquals(
            listOf(QuestionStatus.DIMMED, QuestionStatus.CORRECT, QuestionStatus.WRONG, QuestionStatus.DIMMED),
            answerStatuses(attempt)
        )
        assertTrue(provider.provideData(attempt).questionPanelState.isLocked)
    }

    @Test
    fun answeredQuestion_showsTheExplanationWithTheCorrectLetter() {
        val explanation = provider.provideData(
            QuizAttempt(questions = questions).answer(option = 3)
        ).explanationPanelState

        assertEquals("Εξήγηση", explanation?.text)
        assertEquals("Σωστό: Β", explanation?.helpText)
        assertEquals("Εξήγηση 1", explanation?.description)
    }

    @Test
    fun answeredQuestion_showsTheNextButton() {
        val buttonState = provider.provideData(
            QuizAttempt(questions = questions).answer(option = 1)
        ).buttonState

        assertEquals("Επόμενη", buttonState?.mainButtonText)
    }

    @Test
    fun answeredLastQuestion_hasNoButton() {
        val attempt = QuizAttempt(questions = questions)
            .answer(option = 2).next()
            .answer(option = 4).next()
            .answer(option = 1)

        assertTrue(attempt.isLast)
        assertNull(provider.provideData(attempt).buttonState)
        assertTrue(provider.provideData(attempt).questionPanelState.isLocked)
    }

    @Test
    fun segments_markTheCurrentQuestionAndLeaveTheRestPending() {
        val attempt = QuizAttempt(questions = questions).answer(option = 3)

        assertEquals(
            listOf(SegmentStatus.CURRENT, SegmentStatus.PENDING, SegmentStatus.PENDING),
            segmentStatuses(attempt)
        )
    }

    @Test
    fun segments_showTheResultOfPreviousQuestions() {
        val attempt = QuizAttempt(questions = questions)
            .answer(option = 2).next()
            .answer(option = 1).next()

        assertEquals(
            listOf(SegmentStatus.CORRECT, SegmentStatus.WRONG, SegmentStatus.CURRENT),
            segmentStatuses(attempt)
        )
    }

    @Test
    fun topBar_showsThePositionInTheAttempt() {
        val attempt = QuizAttempt(questions = questions).answer(option = 2).next()

        assertEquals("2/3", provider.provideData(attempt).topBarNavigationState.chipText)
    }

    @Test
    fun emptyState_explainsThatThereAreNoQuestions() {
        val state = provider.provideEmpty()

        assertEquals(
            "Δεν υπάρχουν διαθέσιμες ερωτήσεις για αυτή την ενότητα και δυσκολία.",
            state.questionCardState.question
        )
        assertNull(state.explanationPanelState)
        assertNull(state.buttonState)
    }
}
