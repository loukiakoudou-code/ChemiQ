package com.lkountoura.chemiq.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lkountoura.chemiq.ui.component.common.ButtonActionsEvents
import com.lkountoura.chemiq.ui.component.common.ChemiQButton
import com.lkountoura.chemiq.ui.component.resultScreenPage.LevelCard
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultStatPanel
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultTitle
import com.lkountoura.chemiq.ui.component.resultScreenPage.ScoreRing
import com.lkountoura.chemiq.ui.state.ResultScreenState
import com.lkountoura.chemiq.viewmodel.ResultNavigation
import com.lkountoura.chemiq.viewmodel.ResultScreenViewModel

@Composable
fun ResultScreen(
    viewModel: ResultScreenViewModel = hiltViewModel(),
    onNavigate: (ResultNavigation) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.navigation.collect { event ->
            onNavigate(event)
        }
    }

    when (state) {
        is ResultScreenState.Empty -> {}

        is ResultScreenState.Data -> {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.scoreRingState?.let { state ->
                        item(key = "score") {
                            ScoreRing(state = state)
                        }
                    }

                    state.resultTitleState?.let { state ->
                        item(key = "title") {
                            ResultTitle(state = state)
                        }
                    }

                    state.resultStatPanelState?.let { panelState ->
                        item(key = "stats") {
                            ResultStatPanel(state = panelState)
                        }
                    }

                    state.levelCardState?.let { levelState ->
                        item(key = "levelState") {
                            LevelCard(
                                state = levelState,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
                state.chemiQButtonState?.let { state ->
                    ChemiQButton(
                        state = state,
                        events = ButtonActionsEvents(
                            onMainButtonPressed = { tag ->
                                viewModel.onHomePressed()
                            },
                            onSecondaryButtonPressed = { tag ->
                                viewModel.onPlayAgainPressed()
                            },
                            onTertiaryButtonPressed = { tag ->
                                viewModel.onReviewPressed()
                            }
                        ),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

