package com.lkountoura.chemiq.ui.component.unitSelectionPage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun UnitTab(
    state: UnitTabState,
    modifier: Modifier = Modifier,
    events: UnitTabEvents? = null,
) {
    val onPressed = events?.onPressed
    val shape = RoundedCornerShape(20.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(if (state.isSelected) Color(0xFFEEEAFE) else Color.White)
            .border(
                width = if (state.isSelected) 2.dp else 1.dp,
                color = if (state.isSelected) Color(0xFF5B3DF5) else Color(0xFFE8E6F0),
                shape = shape
            )
            .clickable { events?.onPressed?.invoke(state.tag) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = if (state.isSelected) Color(0xFF5B3DF5) else Color(0xFFEEEAFE),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.index.toString(),
                color = if (state.isSelected) Color.White else Color(0xFF5B3DF5),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = state.title,
                color = Color.Black,
                fontSize = 16.sp
            )
            LinearProgressIndicator(
                progress = { state.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = state.progressColor(state.progress),
                trackColor = Color(0xFFEEEDF3),
                strokeCap = StrokeCap.Round,
                gapSize = 0.dp,
                drawStopIndicator = {}
            )
        }

        Text(
            text = "${(state.progress * 100).roundToInt()}%",
            color = state.progressColor(state.progress),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Immutable
data class UnitTabState(
    val tag: String,
    private val _index: Int = 0,
    private val _title: String = "",
    private val _progress: Float = 0.0f,
    private val _isSelected: Boolean = false,
) {
    val index: Int
        get() = _index
    val title: String
        get() = _title
    val progress: Float
        get() = _progress
    val isSelected: Boolean
        get() = _isSelected


    fun setUnitTabState(
        title: String,
        index: Int,
        progress: Float,
        isSelected: Boolean
    ): UnitTabState = copy(
        _title = title,
        _index = index,
        _progress = progress,
        _isSelected = isSelected
    )

    fun progressColor(progress: Float): Color = when {
        progress >= 0.7f -> Color(0xFF12A150)
        progress >= 0.4f -> Color(0xFFF5A524)
        else -> Color(0xFFE5484D)
    }
}

data class UnitTabEvents(
    val onPressed: ((tag: String) -> Unit)? = null
)
