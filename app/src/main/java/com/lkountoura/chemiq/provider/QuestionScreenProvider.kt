package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.ui.component.common.ButtonLayout
import com.lkountoura.chemiq.ui.component.common.ButtonState
import com.lkountoura.chemiq.ui.component.common.ChipColor
import com.lkountoura.chemiq.ui.component.common.NavigationIcon
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.questionScreenPage.ExplanationPanelState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionCardState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanelState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionProgressPanelState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionStatus
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionTabState
import com.lkountoura.chemiq.ui.component.questionScreenPage.SegmentState
import com.lkountoura.chemiq.ui.component.questionScreenPage.SegmentStatus
import com.lkountoura.chemiq.ui.state.QuestionScreenState
import com.lkountoura.chemiq.viewmodel.QuizAttempt
import javax.inject.Inject

class QuestionScreenProvider @Inject constructor() {

    fun provideLoading(): QuestionScreenState.Loading {
        return QuestionScreenState.Loading(
            topBarNavigationState = provideTopBarNavigationState(chipText = ""),
            questionPanelState = QuestionPanelState(),
            explanationPanelState = null,
            questionCardState = QuestionCardState(),
            questionProgressPanelState = QuestionProgressPanelState(),
            buttonState = null
        )
    }

    fun provideData(attempt: QuizAttempt): QuestionScreenState.Data {
        val question = attempt.currentQuestion
        val selectedOption = attempt.currentSelection
        val isAnswered = selectedOption != null

        val answers = listOf(
            question.option1,
            question.option2,
            question.option3,
            question.option4,
        ).mapIndexed { index, text ->
            val option = index + 1
            QuestionTabState(tag = option.toString()).setQuestionTabState(
                letter = ANSWER_LETTERS[index],
                text = text,
                status = provideAnswerStatus(
                    option = option,
                    selectedOption = selectedOption,
                    correctOption = question.correct
                )
            )
        }

        val segments = List(attempt.questions.size) { index ->
            SegmentState(tag = "segment$index").setSegmentState(
                status = provideSegmentStatus(attempt = attempt, index = index)
            )
        }

        return QuestionScreenState.Data(
            topBarNavigationState = provideTopBarNavigationState(
                chipText = "${attempt.currentIndex + 1}/${attempt.questions.size}"
            ),
            explanationPanelState = if (isAnswered) {
                ExplanationPanelState().setExplanationPanelState(
                    text = "Εξήγηση",
                    helpText = "Σωστό: ${ANSWER_LETTERS.getOrNull(question.correct - 1).orEmpty()}",
                    description = question.explanation
                )
            } else {
                null
            },
            questionCardState = QuestionCardState().setQuestionCardState(
                question = question.text
            ),
            questionProgressPanelState = QuestionProgressPanelState(
                unitSegment = segments
            ),
            questionPanelState = QuestionPanelState(
                questionTab = answers,
                isLocked = isAnswered
            ),
            buttonState = if (isAnswered && !attempt.isLast) {
                ButtonState(
                    tag = "next",
                    _mainButtonText = "Επόμενη",
                    _buttonLayout = ButtonLayout.ONE_BUTTON
                )
            } else {
                null
            }
        )
    }

    fun provideEmpty(): QuestionScreenState.Empty {
        return QuestionScreenState.Empty(
            topBarNavigationState = provideTopBarNavigationState(chipText = ""),
            questionPanelState = QuestionPanelState(),
            explanationPanelState = null,
            questionCardState = QuestionCardState().setQuestionCardState(
                question = "Δεν υπάρχουν διαθέσιμες ερωτήσεις για αυτή την ενότητα και δυσκολία."
            ),
            questionProgressPanelState = QuestionProgressPanelState(),
            buttonState = null
        )
    }

    private fun provideAnswerStatus(
        option: Int,
        selectedOption: Int?,
        correctOption: Int,
    ): QuestionStatus = when {
        selectedOption == null -> QuestionStatus.DEFAULT
        option == correctOption -> QuestionStatus.CORRECT
        option == selectedOption -> QuestionStatus.WRONG
        else -> QuestionStatus.DIMMED
    }

    private fun provideSegmentStatus(attempt: QuizAttempt, index: Int): SegmentStatus = when {
        index == attempt.currentIndex -> SegmentStatus.CURRENT
        index > attempt.currentIndex -> SegmentStatus.PENDING
        attempt.isCorrect(index) == true -> SegmentStatus.CORRECT
        else -> SegmentStatus.WRONG
    }

    private fun provideTopBarNavigationState(chipText: String): TopBarNavigationState =
        TopBarNavigationState(
            navigationIcon = NavigationIcon.CLOSE,
            title = "Ερώτηση",
            chipText = chipText,
            chipColor = ChipColor.PURPLE
        )

    private companion object {
        val ANSWER_LETTERS = listOf("Α", "Β", "Γ", "Δ")
    }
}
