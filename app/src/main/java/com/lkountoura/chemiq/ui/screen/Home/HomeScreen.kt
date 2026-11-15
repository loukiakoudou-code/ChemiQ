package com.lkountoura.chemiq.ui.screen.Home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lkountoura.chemiq.ui.component.balancePage.DifficultySheet
import com.lkountoura.chemiq.ui.component.balancePage.DifficultySheetEvents
import com.lkountoura.chemiq.ui.component.homePage.GamePanel
import com.lkountoura.chemiq.ui.component.homePage.GamePanelEvents
import com.lkountoura.chemiq.ui.component.homePage.Header
import com.lkountoura.chemiq.ui.component.homePage.ReviewBanner
import com.lkountoura.chemiq.viewmodel.HomeNavigation
import com.lkountoura.chemiq.viewmodel.HomeScreenViewModel
import com.lkountoura.chemiq.viewmodel.HomeScreenViewModelActions

@OptIn(ExperimentalMaterial3Api::class)
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

        uiState.reviewBannerState?.let { reviewBannerState ->
            ReviewBanner(
                state = reviewBannerState,
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
    if (uiState.isBottomSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { viewModelActions.onBottomSheetDismissed() },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    width = 36.dp,
                    height = 4.dp,
                    color = Color(0xFFCFCBE0)
                )
            }
        ) {
            DifficultySheet(
                state = uiState.difficultySheetState,
                events = DifficultySheetEvents(
                    onDifficultyPressed = { tag -> viewModelActions.onDifficultyPressed(tag) },
                    onStartPressed = { viewModelActions.onStartPressed() }
                )
            )
        }
    }
}