package com.lkountoura.chemiq.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lkountoura.chemiq.ui.component.balancePage.AtomsTable
import com.lkountoura.chemiq.ui.component.balancePage.BalanceHint
import com.lkountoura.chemiq.ui.component.balancePage.BalanceLabelRow
import com.lkountoura.chemiq.ui.component.balancePage.BalanceMessage
import com.lkountoura.chemiq.ui.component.balancePage.CoefficientStepperEvents
import com.lkountoura.chemiq.ui.component.balancePage.EquationCard
import com.lkountoura.chemiq.ui.component.common.ButtonActionsEvents
import com.lkountoura.chemiq.ui.component.common.ChemiQButton
import com.lkountoura.chemiq.ui.component.common.TopBarNavigation
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationEvents
import com.lkountoura.chemiq.viewmodel.BalancingNavigation
import com.lkountoura.chemiq.viewmodel.BalancingScreenViewModel

@Composable
fun BalancingScreen(
    viewModel: BalancingScreenViewModel = hiltViewModel(),
    onNavigate: (BalancingNavigation) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigation.collect { event ->
            onNavigate(event)
        }
    }

    val events = BalancingScreenEvents(
        onBackPressed = { viewModel.onBackPressed() },
        onMinusPressed = { tag -> viewModel.onMinusPressed(tag) },
        onPlusPressed = { tag -> viewModel.onPlusPressed(tag) },
        onCheckPressed = { viewModel.onCheckPressed() },
        onHintPressed = { viewModel.onHintPressed() }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F4FA))
    )
    {
        uiState.topBarNavigationState?.let { topBarState ->
            TopBarNavigation(
                state = topBarState,
                events = TopBarNavigationEvents(
                    onNavigationPressed = { events.onBackPressed() }
                )
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            uiState.labelRowState?.let { labelRowState ->
                BalanceLabelRow(state = labelRowState)
            }
            uiState.equationCardState?.let { equationCardState ->
                EquationCard(
                    state = equationCardState,
                    events = CoefficientStepperEvents(
                        onMinusPressed = { tag -> events.onMinusPressed(tag) },
                        onPlusPressed = { tag -> events.onPlusPressed(tag) }
                    )
                )
            }
            uiState.atomsTableState?.let { atomsTableState ->
                AtomsTable(state = atomsTableState)
            }
            uiState.balanceMessageState?.let { balanceMessageState ->
                BalanceMessage(state = balanceMessageState)
            }
            BalanceHint()
        }
        uiState.buttonState?.let { buttonState ->
            ChemiQButton(
                state = buttonState,
                events = ButtonActionsEvents(
                    onSecondaryButtonPressed = { events.onHintPressed() },
                    onMainButtonPressed = { events.onCheckPressed() }
                ),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}


data class BalancingScreenEvents(
    val onBackPressed: () -> Unit = {},
    val onMinusPressed: (tag: String) -> Unit = {},
    val onPlusPressed: (tag: String) -> Unit = {},
    val onHintPressed: () -> Unit = {},
    val onCheckPressed: () -> Unit = {},
)
