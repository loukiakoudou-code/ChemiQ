package com.lkountoura.chemiq.ui.component.matchingPage

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
fun MatchPanel(
    state: MatchPanelState,
    modifier: Modifier = Modifier,
    events: MatchPanelEvents? = null,
) {
    val onNameEvents =
        if (state.isLocked) null else MatchTileEvents(onPressed = events?.onNamePressed)
    val onFormulaEvents =
        if (state.isLocked) null else MatchTileEvents(onPressed = events?.onFormulaPressed)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1.4f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            state.names.forEach { tile ->
                MatchTile(state = tile, events = onNameEvents)
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            state.formulas.forEach { tile ->
                MatchTile(state = tile, events = onFormulaEvents)
            }
        }
    }
}


@Immutable
data class MatchPanelState(
    val names: List<MatchTileState> = emptyList(),
    val formulas: List<MatchTileState> = emptyList(),
    val isLocked: Boolean = false,
) {
    fun setMatchPanelState(
        names: List<MatchTileState>,
        formulas: List<MatchTileState>
    ): MatchPanelState =
        copy(
            names = names,
            formulas = formulas
        )
}

data class MatchPanelEvents(
    val onNamePressed: ((tag: String) -> Unit)? = null,
    val onFormulaPressed: ((tag: String) -> Unit)? = null,
)