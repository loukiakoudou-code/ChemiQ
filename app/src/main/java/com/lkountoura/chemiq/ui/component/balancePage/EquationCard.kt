package com.lkountoura.chemiq.ui.component.balancePage

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EquationCard(
    state: EquationCardState,
    modifier: Modifier = Modifier,
    events: CoefficientStepperEvents? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            state.reactants.forEachIndexed { index, species ->
                if (index > 0) EquationSign(text = "+")
                SpeciesItem(state = species, events = events)
            }


            EquationSign(text = "→")


            state.products.forEachIndexed { index, species ->
                if (index > 0) EquationSign(text = "+")
                SpeciesItem(state = species, events = events)
            }
        }
    }
}


@Composable
private fun EquationSign(text: String) {
    Text(
        text = text,
        color = Color(0xFFA9A6BC),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

@Immutable
data class EquationCardState(
    val reactants: List<SpeciesItemState> = emptyList(),
    val products: List<SpeciesItemState> = emptyList()
)

