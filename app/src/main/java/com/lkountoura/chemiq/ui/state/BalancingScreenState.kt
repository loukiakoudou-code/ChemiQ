package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.balancePage.AtomsTableState
import com.lkountoura.chemiq.ui.component.balancePage.BalanceLabelRowState
import com.lkountoura.chemiq.ui.component.balancePage.BalanceMessageState
import com.lkountoura.chemiq.ui.component.balancePage.EquationCardState
import com.lkountoura.chemiq.ui.component.common.ButtonState
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState

@Stable

sealed class BalancingScreenState(
    open val topBarNavigationState: TopBarNavigationState? = null,
    open val labelRowState: BalanceLabelRowState? = null,
    open val equationCardState: EquationCardState? = null,
    open val atomsTableState: AtomsTableState? = null,
    open val balanceMessageState: BalanceMessageState? = null,
    open val buttonState: ButtonState? = null,
) {
    data class Data(
        override val topBarNavigationState: TopBarNavigationState,
        override val labelRowState: BalanceLabelRowState,
        override val equationCardState: EquationCardState,
        override val atomsTableState: AtomsTableState?,
        override val balanceMessageState: BalanceMessageState?,
        override val buttonState: ButtonState,
    ) : BalancingScreenState(
        topBarNavigationState = topBarNavigationState,
        labelRowState = labelRowState,
        equationCardState = equationCardState,
        atomsTableState = atomsTableState,
        balanceMessageState = balanceMessageState,
        buttonState = buttonState,
    )

    data object Empty : BalancingScreenState()
}