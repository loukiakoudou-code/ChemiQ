package com.lkountoura.chemiq.ui.component

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
                .background(state.boxColor, TileShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(state.icon),
                contentDescription = null,
                tint = state.iconColor,
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
    val tag: String = "",
    val result: String = "",
    val title: String = "",
    val boxColor: Color = Color.Unspecified,
    val iconColor: Color = Color.Unspecified,
    @param:DrawableRes val icon: Int = 0,
) {
    fun setResultStatCardState(
        result: String,
        title: String,
        boxColor: Color,
        iconColor: Color,
        @DrawableRes icon: Int,
    ): ResultStatCardState = copy(
        result = result,
        title = title,
        boxColor = boxColor,
        iconColor = iconColor,
        icon = icon,
    )
}