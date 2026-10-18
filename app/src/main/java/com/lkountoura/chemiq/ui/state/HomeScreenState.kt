package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.homePage.GamePanelState
import com.lkountoura.chemiq.ui.component.homePage.HeaderState

@Stable
sealed class HomeScreenState(
    open val gamePanelState: GamePanelState,
    open val headerState: HeaderState
) {

    data class Data(
        override val gamePanelState: GamePanelState,
        override val headerState: HeaderState
    ) : HomeScreenState(
        gamePanelState = gamePanelState,
        headerState = headerState
    )
}