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
import com.lkountoura.chemiq.ui.component.common.TopBarNavigation
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationEvents
import com.lkountoura.chemiq.ui.component.questionScreenPage.ExplanationPanel
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionCard
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanel
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanelEvents
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionProgressPanel
import com.lkountoura.chemiq.viewmodel.QuestionScreenViewModel


@Composable
fun QuestionScreen(
    viewModel: QuestionScreenViewModel = hiltViewModel(),
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
            QuestionProgressPanel(
                state = uiState.questionProgressPanelState,
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
            ExplanationPanel(
                state = uiState.explanationPanelState,
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }

}