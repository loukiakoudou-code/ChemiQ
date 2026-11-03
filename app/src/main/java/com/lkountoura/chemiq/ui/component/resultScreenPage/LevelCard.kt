package com.lkountoura.chemiq.ui.component.resultScreenPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LevelCardShape = RoundedCornerShape(20.dp)
private val BarShape = RoundedCornerShape(5.dp)
private val TextPrimary = Color(0xFF16132B)
private val TextSecondary = Color(0xFF5E5A78)
private val BarTrack = Color(0xFFF0EEF5)
private val LevelPurple = Color(0xFF5B3DF5)
private val LevelOrange = Color(0xFFF5A524)

@Composable
fun LevelCard(
    state: LevelCardState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = LevelCardShape, spotColor = Color(0x2616132B))
            .background(Color.White, LevelCardShape)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state.title,
                color = TextPrimary,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = state.xpText,
                color = TextSecondary,
                fontSize = 13.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Το weight δεν δέχεται 0, γι' αυτό κάθε κομμάτι μπαίνει μόνο όταν είναι > 0.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(BarShape)
                .background(BarTrack)
        ) {
            if (state.before > 0f) {
                Box(
                    modifier = Modifier
                        .weight(state.before)
                        .fillMaxHeight()
                        .background(LevelPurple)
                )
            }
            if (state.gained > 0f) {
                Box(
                    modifier = Modifier
                        .weight(state.gained)
                        .fillMaxHeight()
                        .background(LevelOrange)
                )
            }
            val rest = 1f - state.before - state.gained
            if (rest > 0f) {
                Spacer(modifier = Modifier.weight(rest))
            }
        }

        Text(
            text = state.caption,
            color = LevelOrange,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Immutable
data class LevelCardState(
    val tag: String = "",
    val title: String = "",
    val xpText: String = "",
    val before: Float = 0f,
    val gained: Float = 0f,
    val caption: String = "",
) {
    fun setLevelCardState(
        title: String,
        xpText: String,
        before: Float,
        gained: Float,
        caption: String
    ): LevelCardState = copy(
        title = title,
        xpText = xpText,
        before = before,
        gained = gained,
        caption = caption
    )
}
