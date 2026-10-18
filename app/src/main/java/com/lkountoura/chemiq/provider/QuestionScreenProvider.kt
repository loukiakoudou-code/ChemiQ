package com.lkountoura.chemiq.provider

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
        val explanationPanelState = ExplanationPanelState().setExplanationPanelState(
            text = "Εξήγηση",
            helpText = "Σωστό: ",   // να σεταριστεί από βάση
            description = "ληηωγηυγξκλκνξηκγγκλξκξβγξκξ"        // να σεταριστεί από βάση
        )
        val questionCardState = QuestionCardState().setQuestionCardState(
            question = "jlikuyjftjykuljjkjhcgjk"   // να σεταριστεί από βάση
        )

        val segment1 = SegmentState(
            tag = "segment1",
        ).setSegmentState(
            status = SegmentStatus.CORRECT
        )
        val segment2 = SegmentState(
            tag = "segment2",
        ).setSegmentState(
            status = SegmentStatus.PENDING
        )

        val segment3 = SegmentState(
            tag = "segment3",
        ).setSegmentState(
            status = SegmentStatus.WRONG
        )

        val segment4 = SegmentState(
            tag = "segment4",
        ).setSegmentState(
            status = SegmentStatus.PENDING
        )

        val segment5 = SegmentState(
            tag = "segment5",
        ).setSegmentState(
            status = SegmentStatus.PENDING
        )

        val segment6 = SegmentState(
            tag = "segment6",
        ).setSegmentState(
            status = SegmentStatus.PENDING
        )

        val segment7 = SegmentState(
            tag = "segment7",
        ).setSegmentState(
            status = SegmentStatus.PENDING
        )

        val segment8 = SegmentState(
            tag = "segment8",
        ).setSegmentState(
            status = SegmentStatus.PENDING
        )

        val segment9 = SegmentState(
            tag = "segment9",
        ).setSegmentState(
            status = SegmentStatus.PENDING
        )

        val segment10 = SegmentState(
            tag = "segment10",
        ).setSegmentState(
            status = SegmentStatus.PENDING
        )



        return QuestionScreenState.Data(
            topBarNavigationState = topBarNavigationState,
            explanationPanelState = explanationPanelState,
            questionCardState = questionCardState,
            questionProgressPanelState = QuestionProgressPanelState(
                listOf(
                    segment1,
                    segment2,
                    segment3,
                    segment4,
                    segment5,
                    segment6,
                    segment7,
                    segment8,
                    segment9,
                    segment10
                )
            ),
            questionPanelState = QuestionPanelState(
                listOf(answer1, answer2, answer3, answer4)
            )
        )
    }
}