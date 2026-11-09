package com.lkountoura.chemiq.ui.component.matchingPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MatchProgressCard(
    state: MatchProgressCardState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Match Progress",
                color = Color(0xFF16132B),
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${state.matched}/${state.total}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE8478B)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE8E6F0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = state.progress)
                    .fillMaxHeight()
                    .background(Color(0xFFE8478B), shape = RoundedCornerShape(4.dp))
            )
        }
    }
}


@Immutable
data class MatchProgressCardState(
    val matched: Int = 0,
    val total: Int = 5,
) {
    val progress: Float
        get() = if (total == 0) 0f else matched.toFloat() / total

}