package com.lkountoura.chemiq.ui.component.timeAttackPage

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R

private val TimeOrange = Color(0xFFF97316)
private val TimeOrangeSoft = Color(0xFFFFEDDF)

@Composable
fun TimerPanel(
    state: TimerPanelState,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = state.progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "timeBar"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Γραμμή: ρολόι + χρόνος + σερί
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_timer),
                contentDescription = null,
                tint = TimeOrange,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = state.timeText,
                fontSize = 20.sp,
                lineHeight = 27.sp,
                fontWeight = FontWeight.Bold,
                color = TimeOrange,
                modifier = Modifier.weight(1f)
            )
            if (state.streak >= 2) {
                Row(
                    modifier = Modifier
                        .background(color = TimeOrangeSoft, shape = RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_flame),
                        contentDescription = null,
                        tint = TimeOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Σερί ×${state.streak}",
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TimeOrange
                    )
                }
            }
        }

        // Μπάρα χρόνου
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color(0xFFE8E6F0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = animatedProgress)
                    .fillMaxHeight()
                    .background(color = TimeOrange, shape = RoundedCornerShape(5.dp))
            )
        }
    }
}


data class TimerPanelState(
    val remainingSeconds: Int = 60,
    val totalSeconds: Int = 60,
    val streak: Int = 0,
) {
    val timeText: String
        get() = "%d:%02d".format(remainingSeconds / 60, remainingSeconds % 60)

    val progress: Float
        get() = if (totalSeconds == 0) 0f else remainingSeconds.toFloat() / totalSeconds
}