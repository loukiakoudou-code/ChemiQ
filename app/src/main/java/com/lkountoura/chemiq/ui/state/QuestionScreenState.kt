package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.common.ButtonState
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.questionScreenPage.ExplanationPanelState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionCardState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanelState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionProgressPanelState


@Stable
sealed class QuestionScreenState(
    open val questionPanelState: QuestionPanelState,
    open val topBarNavigationState: TopBarNavigationState,
    open val explanationPanelState: ExplanationPanelState?,
    open val questionCardState: QuestionCardState,
    open val questionProgressPanelState: QuestionProgressPanelState,
    open val buttonState: ButtonState?,
) {
    data class Loading(
        override val questionPanelState: QuestionPanelState,
        override val topBarNavigationState: TopBarNavigationState,
        override val explanationPanelState: ExplanationPanelState?,
        override val questionCardState: QuestionCardState,
        override val questionProgressPanelState: QuestionProgressPanelState,
        override val buttonState: ButtonState?
    ) : QuestionScreenState(
        questionPanelState = questionPanelState,
        topBarNavigationState = topBarNavigationState,
        explanationPanelState = explanationPanelState,
        questionCardState = questionCardState,
        questionProgressPanelState = questionProgressPanelState,
        buttonState = buttonState
    )

    data class Data(
        override val questionPanelState: QuestionPanelState,
        override val topBarNavigationState: TopBarNavigationState,
        override val explanationPanelState: ExplanationPanelState?,
        override val questionCardState: QuestionCardState,
        override val questionProgressPanelState: QuestionProgressPanelState,
        override val buttonState: ButtonState?,
    ) : QuestionScreenState(
        questionPanelState = questionPanelState,
        topBarNavigationState = topBarNavigationState,
        explanationPanelState = explanationPanelState,
        questionCardState = questionCardState,
        questionProgressPanelState = questionProgressPanelState,
        buttonState = buttonState,

    )

    data class Empty(
        override val questionPanelState: QuestionPanelState,
        override val topBarNavigationState: TopBarNavigationState,
        override val explanationPanelState: ExplanationPanelState?,
        override val questionCardState: QuestionCardState,
        override val questionProgressPanelState: QuestionProgressPanelState,
        override val buttonState: ButtonState?
    ) : QuestionScreenState(
        questionPanelState = questionPanelState,
        topBarNavigationState = topBarNavigationState,
        explanationPanelState = explanationPanelState,
        questionCardState = questionCardState,
        questionProgressPanelState = questionProgressPanelState,
        buttonState = buttonState
    )
}
