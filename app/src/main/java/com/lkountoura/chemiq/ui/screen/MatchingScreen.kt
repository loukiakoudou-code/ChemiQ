package com.lkountoura.chemiq.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lkountoura.chemiq.R
import com.lkountoura.chemiq.ui.component.common.TopBarNavigation
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationEvents
import com.lkountoura.chemiq.ui.component.matchingPage.MatchPanel
import com.lkountoura.chemiq.ui.component.matchingPage.MatchPanelEvents
import com.lkountoura.chemiq.ui.component.matchingPage.MatchProgressCard
import com.lkountoura.chemiq.viewmodel.MatchingNavigation
import com.lkountoura.chemiq.viewmodel.MatchingScreenViewModel

@Composable
fun MatchingScreen(
    viewModel: MatchingScreenViewModel = hiltViewModel(),
    onNavigate: (MatchingNavigation) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigation.collect { event ->
            onNavigate(event)
        }
    }

    LifecycleStartEffect(Unit) {
        viewModel.onScreenStarted()
        onStopOrDispose { viewModel.onScreenStopped() }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        uiState.topBarNavigationState?.let { topBarState ->
            TopBarNavigation(
                state = topBarState,
                events = TopBarNavigationEvents(
                    onNavigationPressed = {
                        viewModel.onClosePressed()
                    }
                )
            )
        }

        Text(
            text = "Πάτησε ένα όνομα και μετά τον χημικό τύπο του",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF5E5A78)
        )

        uiState.matchPanelState?.let { matchPanelState ->
            MatchPanel(
                state = matchPanelState,
                events = MatchPanelEvents(
                    onNamePressed = {
                        viewModel.onNamePressed(it)
                    },
                    onFormulaPressed = {
                        viewModel.onFormulaPressed(it)
                    }
                )
            )
        }

        uiState.matchProgressCardState?.let { matchProgressCardState ->
            MatchProgressCard(
                state = matchProgressCardState
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_lightbulb),
                contentDescription = null,
                tint = Color(0xFF9A97AE),
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "Τα σωστά ζεύγη κλειδώνουν. Όσο λιγότερες κινήσεις, τόσο περισσότερα XP.",
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF9A97AE)
            )
        }


    }

}

