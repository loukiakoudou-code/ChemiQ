package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.homePage.GamePanelState
import com.lkountoura.chemiq.ui.component.homePage.HeaderState
import com.lkountoura.chemiq.ui.component.homePage.ReviewBannerState

@Stable
sealed class HomeScreenState(
    open val gamePanelState: GamePanelState,
    open val headerState: HeaderState,
    open val reviewBannerState: ReviewBannerState?
) {

    data class Data(
        override val gamePanelState: GamePanelState,
        override val headerState: HeaderState,
        override val reviewBannerState: ReviewBannerState?
    ) : HomeScreenState(
        gamePanelState = gamePanelState,
        headerState = headerState,
        reviewBannerState = reviewBannerState
    )
}
