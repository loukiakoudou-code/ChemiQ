package com.lkountoura.chemiq.ui.component.balancePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AtomsTable(
    state: AtomsTableState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        val headerColor = Color(0xFF8A87A0)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEFEEF5))
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                text = "ΑΤΟΜΟ",
                modifier = Modifier.width(64.dp),
                fontSize = 11.sp,
                color = headerColor
            )
            Text(
                text = "ΑΝΤΙΔΡΩΝΤΑ",
                modifier = Modifier.weight(1f),
                fontSize = 11.sp,
                color = headerColor,
                textAlign = TextAlign.Center
            )
            Text(
                text = "ΠΡΟΪΟΝΤΑ",
                modifier = Modifier.weight(1f),
                fontSize = 11.sp,
                color = headerColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(20.dp))
        }


        state.rows.forEach { row ->
            HorizontalDivider(color = Color(0xFFE8E6F0))
            AtomRow(state = row)
        }
    }
}

data class AtomsTableState(
    val rows: List<AtomRowState> = emptyList()
)