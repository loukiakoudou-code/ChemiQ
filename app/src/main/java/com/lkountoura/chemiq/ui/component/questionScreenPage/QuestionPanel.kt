package com.lkountoura.chemiq.ui.component.questionScreenPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun QuestionPanel(
    state: QuestionPanelState,
    modifier: Modifier = Modifier,
    events: QuestionPanelEvents? = null,
) {
    val tabEvents = if (state.isLocked) null else QuestionTabEvents(onPressed = events?.onPressed)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            state.questionTab.forEach { tab ->
                QuestionTab(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    state = tab,
                    events = tabEvents
                )
            }
        }
    }
}

@Immutable
data class QuestionPanelState(
    val questionTab: List<QuestionTabState> = emptyList(),
    val isLocked: Boolean = false,
) {
    fun setQuestionPanelState(questionTab: List<QuestionTabState>): QuestionPanelState =
        copy(questionTab = questionTab)
}

data class QuestionPanelEvents(
    val onPressed: ((tag: String) -> Unit)? = null
)