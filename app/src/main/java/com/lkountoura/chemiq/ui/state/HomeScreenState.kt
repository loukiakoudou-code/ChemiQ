package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.balancePage.DifficultySheetState
import com.lkountoura.chemiq.ui.component.homePage.GamePanelState
import com.lkountoura.chemiq.ui.component.homePage.HeaderState
import com.lkountoura.chemiq.ui.component.homePage.ReviewBannerState

@Stable
sealed class HomeScreenState(
    open val gamePanelState: GamePanelState,
    open val headerState: HeaderState,
    open val reviewBannerState: ReviewBannerState?,
    open val isBottomSheetOpen: Boolean,
    open val difficultySheetState: DifficultySheetState
) {

    data class Data(
        override val gamePanelState: GamePanelState,
        override val headerState: HeaderState,
        override val reviewBannerState: ReviewBannerState?,
        override val isBottomSheetOpen: Boolean = false,
        override val difficultySheetState: DifficultySheetState
    ) : HomeScreenState(
        gamePanelState = gamePanelState,
        headerState = headerState,
        reviewBannerState = reviewBannerState,
        isBottomSheetOpen = isBottomSheetOpen,
        difficultySheetState = difficultySheetState
    )
}
