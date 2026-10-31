package com.lkountoura.chemiq.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lkountoura.chemiq.ui.component.common.ButtonActionsEvents
import com.lkountoura.chemiq.ui.component.common.ChemiQButton
import com.lkountoura.chemiq.ui.component.common.TopBarNavigation
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationEvents
import com.lkountoura.chemiq.ui.component.questionScreenPage.ExplanationPanel
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionCard
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanel
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionPanelEvents
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionProgressPanel
import com.lkountoura.chemiq.ui.component.questionScreenPage.QuestionSkeleton
import com.lkountoura.chemiq.ui.state.QuestionScreenState
import com.lkountoura.chemiq.viewmodel.QuestionScreenViewModel


@Composable
fun QuestionScreen(
    viewModel: QuestionScreenViewModel = hiltViewModel(),
    onClosePressed: (() -> Unit)? = null,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LifecycleStartEffect(Unit) {
        viewModel.onScreenStarted()
        onStopOrDispose { viewModel.onScreenStopped() }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopBarNavigation(
            state = uiState.topBarNavigationState,
            events = TopBarNavigationEvents(
                onNavigationPressed = {
                    onClosePressed?.invoke()
                }
            )
        )
        when (uiState) {
            is QuestionScreenState.Loading -> {
                QuestionSkeleton()
            }

            is QuestionScreenState.Empty -> {
                QuestionCard(
                    state = uiState.questionCardState,
                    modifier = Modifier.padding(all = 16.dp)
                )
            }

            is QuestionScreenState.Data -> {
                val scrollState = rememberScrollState()
                val question = uiState.questionCardState.question
                val isAnswered = uiState.explanationPanelState != null

                LaunchedEffect(question, isAnswered) {
                    if (isAnswered) {
                        // Wait one frame so maxValue includes the newly shown explanation.
                        withFrameNanos { }
                        scrollState.animateScrollTo(scrollState.maxValue)
                    } else {
                        scrollState.scrollTo(0)
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
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
                            onPressed = { tag ->
                                viewModel.onAnswerPressed(tag)
                            }
                        )
                    )

                    uiState.explanationPanelState?.let { explanationPanelState ->
                        ExplanationPanel(
                            state = explanationPanelState,
                            modifier = Modifier.padding(all = 16.dp)
                        )
                    }
                }

                uiState.buttonState?.let { buttonState ->
                    ChemiQButton(
                        state = buttonState,
                        events = ButtonActionsEvents(
                            onMainButtonPressed = {
                                viewModel.onNextPressed()
                            }
                        ),
                        modifier = Modifier.padding(all = 16.dp)
                    )
                }
            }
        }
    }
}
