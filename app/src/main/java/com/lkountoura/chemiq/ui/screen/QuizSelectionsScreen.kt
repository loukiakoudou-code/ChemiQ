package com.lkountoura.chemiq.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lkountoura.chemiq.ui.component.common.ButtonActionsEvents
import com.lkountoura.chemiq.ui.component.common.ChemiQButton
import com.lkountoura.chemiq.ui.component.common.TopBarNavigation
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationEvents
import com.lkountoura.chemiq.ui.component.unitSelectionPage.DifficultyPanel
import com.lkountoura.chemiq.ui.component.unitSelectionPage.DifficultyPanelEvents
import com.lkountoura.chemiq.ui.component.unitSelectionPage.QuizSelectionSkeleton
import com.lkountoura.chemiq.ui.component.unitSelectionPage.UnitPanel
import com.lkountoura.chemiq.ui.component.unitSelectionPage.UnitPanelEvents
import com.lkountoura.chemiq.ui.state.QuizUnitSelectionScreenState
import com.lkountoura.chemiq.viewmodel.QuizSelectionScreenViewModel

@Composable
fun QuizSelectionScreen(
    viewModel: QuizSelectionScreenViewModel = hiltViewModel(),
    onBackPressed: (() -> Unit)? = null,
    onStart: ((unitId: Int, difficulty: String) -> Unit)? = null,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedUnit = uiState.unitPanelState.unitTabs.firstOrNull { it.isSelected }
    val selectedDifficulty =
        uiState.difficultyPanelState.difficultyBoxes.firstOrNull { it.isSelected }


    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(90.dp)
    ) {
        Column() {
            TopBarNavigation(
                state = uiState.topBarNavigationState,
                events = TopBarNavigationEvents(
                    onNavigationPressed = {
                        onBackPressed?.invoke()
                    }
                )

            )

            when (uiState) {
                is QuizUnitSelectionScreenState.Loading -> {
                    QuizSelectionSkeleton()
                }

                is QuizUnitSelectionScreenState.Data -> {
                    UnitPanel(
                        state = uiState.unitPanelState,
                        events = UnitPanelEvents(
                            onPressed = { tag ->
                                viewModel.onUnitTabPressed(tag)
                            }
                        )
                    )

                    DifficultyPanel(
                        state = uiState.difficultyPanelState,
                        events = DifficultyPanelEvents(
                            onPressed = { tag ->
                                viewModel.onDifficultyBoxPressed(tag)
                            }
                        ),
                        modifier = Modifier.padding(all = 16.dp)
                    )
                }
            }
        }

        if (uiState is QuizUnitSelectionScreenState.Data) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ChemiQButton(
                    state = uiState.buttonState,
                    events = ButtonActionsEvents(
                        onMainButtonPressed = {
                            if (selectedUnit != null && selectedDifficulty != null) {
                                onStart?.invoke(selectedUnit.index, selectedDifficulty.tag)
                            }
                        }
                    ),
                    modifier = Modifier.padding(all = 16.dp)

                )
            }
        }
    }
}