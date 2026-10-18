package com.lkountoura.chemiq.ui.component.homePage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R

val CardShape = RoundedCornerShape(20.dp)
private val TileShape = RoundedCornerShape(14.dp)
private val TextPrimary = Color(0xFF16132B)
private val TextSecondary = Color(0xFF5E5A78)

@Composable
fun GameTab(
    state: GameTabState,
    modifier: Modifier = Modifier,
    events: GameTabEvents? = null,
) {
    val onPressed = events?.onPressed

    Column(
        modifier = modifier
            .shadow(elevation = 4.dp, shape = CardShape, spotColor = Color(0x2616132B))
            .background(Color.White, CardShape)
            .clickable(enabled = onPressed != null) { onPressed?.invoke(state.tag) }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(state.boxColor, TileShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(state.icon),
                contentDescription = null,
                tint = state.iconColor,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = state.title,
                color = TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = state.description,
                color = TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Immutable
data class GameTabState(
    val tag: String = "",
    val title: String = "",
    val description: String = "",
    val boxColor: Color = Color.Unspecified,
    val iconColor: Color = Color.Unspecified,
    @param:DrawableRes val icon: Int = 0,
) {
    fun setGameState(
        title: String,
        description: String,
        boxColor: Color,
        iconColor: Color,
        @DrawableRes icon: Int,
    ): GameTabState = copy(
        title = title,
        description = description,
        boxColor = boxColor,
        iconColor = iconColor,
        icon = icon,
    )
}

data class GameTabEvents(
    val onPressed: ((tag: String) -> Unit)? = null
)

@Preview(showBackground = true, backgroundColor = 0xFFF7F6FB)
@Composable
private fun GameTabPreview() {
    Box(Modifier.padding(16.dp)) {
        GameTab(
            state = GameTabState(tag = "quiz").setGameState(
                title = "Quiz",
                description = "10 ερωτήσεις ανά ενότητα",
                boxColor = Color(0xFFEEEAFE),
                iconColor = Color(0xFF5B3DF5),
                icon = R.drawable.ic_flask,
            ),
            modifier = Modifier.width(180.dp)
        )
    }
}