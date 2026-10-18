package com.lkountoura.chemiq.ui.component.unitSelectionPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DifficultyPanel(
    state: DifficultyPanelState,
    modifier: Modifier = Modifier,
    events: DifficultyPanelEvents? = null,
) {
    val boxEvents = DifficultyBoxEvents(onPressed = events?.onPressed)
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Δυσκολία",
            color = Color(0xFF16132B),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFFF0EEF5), RoundedCornerShape(14.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            state.difficultyBoxes.forEach { box ->
                DifficultyBox(
                    state = box,
                    events = boxEvents,
                    modifier = Modifier.weight(1f)
                )
            }

        }

    }
}

@Immutable
data class DifficultyPanelState(
    val difficultyBoxes: List<DifficultyBoxState> = emptyList(),
)

data class DifficultyPanelEvents(
    val onPressed: ((tag: String) -> Unit)? = null
)
