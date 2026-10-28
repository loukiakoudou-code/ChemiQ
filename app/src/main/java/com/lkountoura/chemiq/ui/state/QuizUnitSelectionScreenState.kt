package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.common.ButtonState
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.unitSelectionPage.DifficultyPanelState
import com.lkountoura.chemiq.ui.component.unitSelectionPage.UnitPanelState

@Stable
sealed class QuizUnitSelectionScreenState(
    open val unitPanelState: UnitPanelState,
    open val topBarNavigationState: TopBarNavigationState,
    open val difficultyPanelState: DifficultyPanelState,
    open val buttonState: ButtonState

) {
    data class Loading(
        override val unitPanelState: UnitPanelState,
        override val topBarNavigationState: TopBarNavigationState,
        override val difficultyPanelState: DifficultyPanelState,
        override val buttonState: ButtonState
    ) : QuizUnitSelectionScreenState(
        unitPanelState = unitPanelState,
        topBarNavigationState = topBarNavigationState,
        difficultyPanelState = difficultyPanelState,
        buttonState = buttonState
    )

    data class Data(
        override val unitPanelState: UnitPanelState,
        override val topBarNavigationState: TopBarNavigationState,
        override val difficultyPanelState: DifficultyPanelState,
        override val buttonState: ButtonState
    ) : QuizUnitSelectionScreenState(
        unitPanelState = unitPanelState,
        topBarNavigationState = topBarNavigationState,
        difficultyPanelState = difficultyPanelState,
        buttonState = buttonState
    )
}