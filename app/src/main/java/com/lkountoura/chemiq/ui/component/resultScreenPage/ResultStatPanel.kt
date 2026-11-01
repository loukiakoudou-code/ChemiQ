package com.lkountoura.chemiq.ui.component.resultScreenPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultStatPanel(
    state: ResultStatPanelState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.resultStatCard.forEach { card ->
                ResultStatCard(
                    modifier = Modifier.weight(1f),
                    state = card
                )
            }
        }
    }
}

@Immutable
data class ResultStatPanelState(
    val resultStatCard: List<ResultStatCardState> = emptyList(),
) {
    fun setResultStatPanelState(resultStatCard: List<ResultStatCardState>): ResultStatPanelState =
        copy(resultStatCard = resultStatCard)
}