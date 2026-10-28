package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionCardState
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanelState
import com.lkountoura.chemiq.ui.component.timeAttackPage.TimerPanelState

@Stable
sealed class TimeAttackScreenState(
    open val questionPanelState: QuestionPanelState,
    open val topBarNavigationState: TopBarNavigationState,
    open val questionCardState: QuestionCardState,
    open val timePanelState: TimerPanelState,
) {

    data class Data(
        override val questionPanelState: QuestionPanelState,
        override val topBarNavigationState: TopBarNavigationState,
        override val questionCardState: QuestionCardState,
        override val timePanelState: TimerPanelState,
    ) : TimeAttackScreenState(
        questionPanelState = questionPanelState,
        topBarNavigationState = topBarNavigationState,
        questionCardState = questionCardState,
        timePanelState = timePanelState,
    )
}