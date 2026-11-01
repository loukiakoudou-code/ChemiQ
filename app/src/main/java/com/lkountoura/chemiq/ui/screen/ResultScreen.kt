package com.lkountoura.chemiq.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultStatPanel
import com.lkountoura.chemiq.ui.state.ResultScreenState
import com.lkountoura.chemiq.viewmodel.ResultScreenViewModel

@Composable
fun ResultScreen(
    viewModel: ResultScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (state) {
        is ResultScreenState.Empty -> {}

        is ResultScreenState.Data -> {
            state.resultStatPanelState?.let { state ->
                ResultStatPanel(state = state)
            }
        }
    }

}