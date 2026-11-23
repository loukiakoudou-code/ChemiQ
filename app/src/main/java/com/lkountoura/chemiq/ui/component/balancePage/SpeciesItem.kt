package com.lkountoura.chemiq.ui.component.balancePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SpeciesItem(
    state: SpeciesItemState,
    modifier: Modifier = Modifier,
    events: CoefficientStepperEvents? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = state.formula,
            color = Color(0xFF16132B),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        CoefficientStepper(
            state = state.stepperState,
            events = events
        )
    }
}


@Immutable
data class SpeciesItemState(
    val formula: String = "",
    val stepperState: CoefficientStepperState = CoefficientStepperState(tag = "")
)