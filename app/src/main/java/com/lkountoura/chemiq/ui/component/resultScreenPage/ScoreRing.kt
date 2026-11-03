package com.lkountoura.chemiq.ui.component.resultScreenPage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TextPrimary = Color(0xFF16132B)
private val TextSecondary = Color(0xFF5E5A78)
private val RingTrack = Color(0xFFE7E4F0)
private val RingGreen = Color(0xFF12A150)

private data class Confetti(
    val dx: Float,
    val dy: Float,
    val width: Float,
    val height: Float,
    val color: Color,
    val isDot: Boolean,
)

private val ConfettiPieces = listOf(
    Confetti(
        dx = -141f,
        dy = -65f,
        width = 10f,
        height = 10f,
        color = Color(0xFFF97316),
        isDot = true
    ),
    Confetti(
        dx = -112f,
        dy = 20f,
        width = 8f,
        height = 4f,
        color = Color(0xFF5B3DF5),
        isDot = false
    ),
    Confetti(
        dx = 120f,
        dy = -54f,
        width = 12f,
        height = 12f,
        color = Color(0xFFE8478B),
        isDot = true
    ),
    Confetti(
        dx = 138f,
        dy = 40f,
        width = 8f,
        height = 4f,
        color = Color(0xFF2F7BF6),
        isDot = false
    ),
    Confetti(
        dx = -165f,
        dy = -7f,
        width = 6f,
        height = 6f,
        color = Color(0xFFF5A524),
        isDot = true
    ),
    Confetti(dx = 167f, dy = -2f, width = 6f, height = 6f, color = Color(0xFF0EA5A0), isDot = true),
    Confetti(
        dx = 102f,
        dy = -90f,
        width = 6f,
        height = 3f,
        color = Color(0xFFF5A524),
        isDot = false
    ),
    Confetti(
        dx = -121f,
        dy = 80f,
        width = 10f,
        height = 10f,
        color = Color(0xFF0EA5A0),
        isDot = true
    ),
    Confetti(dx = 117f, dy = 84f, width = 7f, height = 7f, color = Color(0xFF5B3DF5), isDot = true),
)

@Composable
fun ScoreRing(
    state: ScoreRingState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            ConfettiPieces.forEach { piece ->
                val position = center + Offset(piece.dx.dp.toPx(), piece.dy.dp.toPx())
                val w = piece.width.dp.toPx()
                val h = piece.height.dp.toPx()

                if (piece.isDot) {
                    drawCircle(
                        color = piece.color,
                        radius = w / 2,
                        center = position
                    )
                } else {
                    rotate(degrees = -30f, pivot = position) {
                        drawRoundRect(
                            color = piece.color,
                            topLeft = position - Offset(w / 2, h / 2),
                            size = Size(w, h),
                            cornerRadius = CornerRadius(1.dp.toPx())
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.size(184.dp),
            contentAlignment = Alignment.Center
        ) {

            Canvas(modifier = Modifier.matchParentSize()) {
                val stroke = 13.dp.toPx()
                val topLeft = Offset(stroke / 2, stroke / 2)
                val arcSize = Size(size.width - stroke, size.height - stroke)

                drawArc(
                    color = RingTrack,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = stroke)
                )
                drawArc(
                    color = RingGreen,
                    startAngle = -90f,
                    sweepAngle = 360f * state.progress,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = stroke)
                )
            }


            Box(
                modifier = Modifier
                    .size(140.dp)
                    .shadow(elevation = 6.dp, shape = CircleShape, spotColor = Color(0x2616132B))
                    .background(Color.White, CircleShape)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = state.score,
                    color = TextPrimary,
                    fontSize = 48.sp,
                    lineHeight = 52.sp,
                    letterSpacing = (-1).sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = state.description,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Immutable
data class ScoreRingState(
    val score: String = "",
    val description: String = "",
    val progress: Float = 0f,
)