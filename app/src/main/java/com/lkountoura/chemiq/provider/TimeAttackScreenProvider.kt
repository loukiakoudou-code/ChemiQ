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
import javax.inject.Inject

class TimeAttackScreenProvider @Inject constructor() {
    fun provideData(): TimeAttackScreenState {
        val answer1 = QuestionTabState(tag = "answer1").setQuestionTabState(
            letter = "A",
            text = "Οξυγόνο",
            status = QuestionStatus.DEFAULT
        )
        val answer2 = QuestionTabState(tag = "answer2").setQuestionTabState(
            letter = "B",
            text = "",
            status = QuestionStatus.DEFAULT
        )
        val answer3 = QuestionTabState(tag = "answer3").setQuestionTabState(
            letter = "C",
            text = "",
            status = QuestionStatus.DEFAULT
        )
        val answer4 = QuestionTabState(tag = "answer4").setQuestionTabState(
            letter = "D",
            text = "",
            status = QuestionStatus.DEFAULT
        )

        val topBarNavigationState = TopBarNavigationState(
            navigationIcon = NavigationIcon.CLOSE,
            title = "Time Attack",
            chipText = "7 σωστές", //ο αριθμος να ερχεται αναλογα το σκορ της εκαστοτε στιγμης
            chipColor = ChipColor.GREEN,
            chipIcon = ChipIcon.CHECK
        )
        val questionCardState = QuestionCardState().setQuestionCardState(
            question = "jlikuyjftjykuljjkjhcgjk"   // να σεταριστεί από βάση
        )
        return TimeAttackScreenState.Data(
            topBarNavigationState = topBarNavigationState,
            questionCardState = questionCardState,
            timePanelState = TimerPanelState(
                remainingSeconds = 60,
                totalSeconds = 60,
                streak = 0,
            ),
            questionPanelState = QuestionPanelState(
                listOf(answer1, answer2, answer3, answer4)
            )
        )
    }
}
