package com.lkountoura.chemiq.ui.component.questionScreenPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuestionProgressPanel(
    state: QuestionProgressPanelState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        state.unitSegment.forEach { segment ->
            ProgressSegment(state = segment, modifier = Modifier.weight(1f))
        }
    }
}

@Immutable
data class QuestionProgressPanelState(
    val unitSegment: List<SegmentState> = emptyList(),
) {
    fun setQuestionProgressPanelState(unitSegment: List<SegmentState>): QuestionProgressPanelState =
        copy(unitSegment = unitSegment)
}
