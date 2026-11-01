package com.lkountoura.chemiq.ui.component.resultScreenPage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R

private val StatCardShape = RoundedCornerShape(16.dp)
private val TileShape = RoundedCornerShape(10.dp)
private val TextPrimary = Color(0xFF16132B)
private val TextSecondary = Color(0xFF5E5A78)

@Composable
fun ResultStatCard(
    state: ResultStatCardState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .shadow(elevation = 4.dp, shape = StatCardShape, spotColor = Color(0x2616132B))
            .background(Color.White, StatCardShape)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(state.statType.backgroundColor, TileShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(state.statType.icon),
                contentDescription = null,
                tint = state.statType.iconColor,
                modifier = Modifier.size(18.dp)
            )
        }

        Text(
            text = state.result,
            color = TextPrimary,
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = state.title,
            color = TextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Immutable
data class ResultStatCardState(
    val result: String = "",
    val title: String = "",
    val statType: StatType,
) {
    fun setResultStatCardState(
        result: String,
        title: String,
        statType: StatType
    ): ResultStatCardState = copy(
        result = result,
        title = title,
        statType = statType
    )
}

enum class StatType(
    @param:DrawableRes val icon: Int,
    val iconColor: Color,
    val backgroundColor: Color
) {
    XP(R.drawable.ic_star, Color(0xFFF5A524), Color(0xFFFFF4DE)),
    ACCURACY(R.drawable.ic_target, Color(0xFF12A150), Color(0xFFE3F7EC)),
    TIME(R.drawable.ic_timer, Color(0xFF5B3DF5), Color(0xFFEEEAFE))
}