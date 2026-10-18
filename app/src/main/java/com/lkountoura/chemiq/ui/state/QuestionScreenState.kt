package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanelState


@Stable
sealed class QuestionScreenState(
    open val questionPanelState: QuestionPanelState,
    open val topBarNavigationState: TopBarNavigationState,
) {
    data class Data(
        override val questionPanelState: QuestionPanelState,
        override val topBarNavigationState: TopBarNavigationState,
    ) : QuestionScreenState(
        questionPanelState = questionPanelState,
        topBarNavigationState = topBarNavigationState,
    )
}
