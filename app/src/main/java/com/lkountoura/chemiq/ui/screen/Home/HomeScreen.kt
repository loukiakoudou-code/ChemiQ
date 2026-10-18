package com.lkountoura.chemiq.ui.screen.Home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lkountoura.chemiq.ui.component.homePage.GamePanel
import com.lkountoura.chemiq.ui.component.homePage.GamePanelEvents
import com.lkountoura.chemiq.ui.component.homePage.Header
import com.lkountoura.chemiq.viewmodel.HomeNavigation
import com.lkountoura.chemiq.viewmodel.HomeScreenViewModel
import com.lkountoura.chemiq.viewmodel.HomeScreenViewModelActions

@Composable
fun HomeScreen(
    onNavigate: (HomeNavigation) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val viewModelActions: HomeScreenViewModelActions = viewModel

    LaunchedEffect(Unit) {
        viewModel.navigation.collect { event ->
            onNavigate(event)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(
            state = uiState.headerState
        )

        GamePanel(
            state = uiState.gamePanelState,
            events = GamePanelEvents(
                onPressed = { tag ->
                    viewModelActions.onGameTabPressed(tag)
                }
            )
        )
    }

}