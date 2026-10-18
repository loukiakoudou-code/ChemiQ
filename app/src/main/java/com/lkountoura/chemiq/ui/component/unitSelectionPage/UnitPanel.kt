package com.lkountoura.chemiq.ui.component.unitSelectionPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UnitPanel(
    state: UnitPanelState,
    modifier: Modifier = Modifier,
    events: UnitPanelEvents? = null,
) {
    val tabEvents = UnitTabEvents(onPressed = events?.onPressed)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            state.text?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            state.helpText?.let {
                Text(
                    text = it,
                    fontSize = 14.sp
                )
            }
        }

        state.unitTabs.forEach { tab ->
            UnitTab(
                modifier = Modifier.padding(horizontal = 16.dp),
                state = tab,
                events = tabEvents
            )
        }
    }
}


@Immutable
data class UnitPanelState(
    val text: String? = null,
    val helpText: String? = null,
    val unitTabs: List<UnitTabState> = emptyList(),

) {
    fun setUnitPanelState(unitTabs: List<UnitTabState>): UnitPanelState =
        copy(unitTabs = unitTabs)
}

data class UnitPanelEvents(
    val onPressed: ((tag: String) -> Unit)? = null
)
