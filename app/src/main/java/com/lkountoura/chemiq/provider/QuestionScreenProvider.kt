package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.ui.component.common.ChipColor
import com.lkountoura.chemiq.ui.component.common.NavigationIcon
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanelState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionStatus
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionTabState
import com.lkountoura.chemiq.ui.state.QuestionScreenState
import javax.inject.Inject

class QuestionScreenProvider @Inject constructor() {
    fun provideData(): QuestionScreenState {
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
            navigationIcon = NavigationIcon.BACK,
            title = "Ερώτηση",
            chipText = "1/10",
            chipColor = ChipColor.PURPLE
        )


        return QuestionScreenState.Data(
            topBarNavigationState = topBarNavigationState,
            questionPanelState = QuestionPanelState(
                listOf(answer1, answer2, answer3, answer4)
            )
        )
    }
}