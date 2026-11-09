package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.matchingPage.MatchPanelState
import com.lkountoura.chemiq.ui.component.matchingPage.MatchProgressCardState

@Stable

sealed class MatchingScreenState(
    open val topBarNavigationState: TopBarNavigationState? = null,
    open val matchPanelState: MatchPanelState? = null,
    open val matchProgressCardState: MatchProgressCardState? = null,
) {
    data class Data(
        override val topBarNavigationState: TopBarNavigationState,
        override val matchPanelState: MatchPanelState,
        override val matchProgressCardState: MatchProgressCardState,
    ) : MatchingScreenState(
        topBarNavigationState = topBarNavigationState,
        matchPanelState = matchPanelState,
        matchProgressCardState = matchProgressCardState,
    )

    data object Empty : MatchingScreenState()
}


