package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.ui.component.common.ChipColor
import com.lkountoura.chemiq.ui.component.common.ChipIcon
import com.lkountoura.chemiq.ui.component.common.NavigationIcon
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionCardState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanelState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionStatus
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionTabState
import com.lkountoura.chemiq.ui.component.timeAttackPage.TimerPanelState
import com.lkountoura.chemiq.ui.state.TimeAttackScreenState
import com.lkountoura.chemiq.viewmodel.helpers.TimeAttackRun
import javax.inject.Inject

class TimeAttackScreenProvider @Inject constructor() {

    fun provideEmpty(): TimeAttackScreenState = TimeAttackScreenState.Empty

    fun provideData(run: TimeAttackRun? = null): TimeAttackScreenState {
        val answer1 = QuestionTabState(tag = "1").setQuestionTabState(
            letter = "A",
            text = run?.currentQuestion?.option1 ?: "",
            status = provideAnswerStatus(option = 1, run = run)
        )
        val answer2 = QuestionTabState(tag = "2").setQuestionTabState(
            letter = "B",
            text = run?.currentQuestion?.option2 ?: "",
            status = provideAnswerStatus(option = 2, run = run)
        )
        val answer3 = QuestionTabState(tag = "3").setQuestionTabState(
            letter = "Γ",
            text = run?.currentQuestion?.option3 ?: "",
            status = provideAnswerStatus(option = 3, run = run)
        )
        val answer4 = QuestionTabState(tag = "4").setQuestionTabState(
            letter = "Δ",
            text = run?.currentQuestion?.option4 ?: "",
            status = provideAnswerStatus(option = 4, run = run)
        )

        val topBarNavigationState = TopBarNavigationState(
            navigationIcon = NavigationIcon.CLOSE,
            title = "Time Attack",
            chipText = "${run?.correctCount.toString()} Σωστές",
            chipColor = ChipColor.GREEN,
            chipIcon = ChipIcon.CHECK
        )
        val questionCardState = QuestionCardState().setQuestionCardState(
            question = run?.currentQuestion?.text ?: ""
        )
        return TimeAttackScreenState.Data(
            topBarNavigationState = topBarNavigationState,
            questionCardState = questionCardState,
            timePanelState = TimerPanelState(
                remainingSeconds = run?.remainingSeconds ?: 60,
                totalSeconds = TimeAttackRun.TOTAL_SECONDS,
                streak = run?.streak ?: 0,
            ),
            questionPanelState = QuestionPanelState(
                questionTab = listOf(answer1, answer2, answer3, answer4),
                isLocked = run?.isAnswered == true
            )
        )
    }

    private fun provideAnswerStatus(option: Int, run: TimeAttackRun?): QuestionStatus {
        val selectedOption = run?.selectedOption
        val correctOption = run?.currentQuestion?.correct

        return when {
            selectedOption == null -> QuestionStatus.DEFAULT
            option == correctOption -> QuestionStatus.CORRECT
            option == selectedOption -> QuestionStatus.WRONG
            else -> QuestionStatus.DIMMED
        }
    }

}
