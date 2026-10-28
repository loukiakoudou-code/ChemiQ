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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lkountoura.chemiq.R
import com.lkountoura.chemiq.ui.component.common.TopBarNavigation
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationEvents
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionCard
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanel
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanelEvents
import com.lkountoura.chemiq.ui.component.timeAttackPage.TimerPanel
import com.lkountoura.chemiq.viewmodel.TimeAttackScreenViewModel

@Composable
fun TimeAttackScreen(
    viewModel: TimeAttackScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(90.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            TopBarNavigation(
                state = uiState.topBarNavigationState,
                events = TopBarNavigationEvents(
                    onNavigationPressed = {
//                    viewModel.onNavigationPressed()
                    }
                )
            )
            TimerPanel(
                state = uiState.timePanelState,
                modifier = Modifier.padding(all = 16.dp)
            )

            QuestionCard(
                state = uiState.questionCardState,
                modifier = Modifier.padding(all = 16.dp)
            )

            QuestionPanel(
                state = uiState.questionPanelState,
                events = QuestionPanelEvents(
                    onPressed = {
//                    viewModel.onQuestionTabPressed(it)
                    }
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_zap),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Απάντησε όσες περισσότερες μπορείς σε 60″",
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF9A97AE)
                )
            }
        }
    }
}