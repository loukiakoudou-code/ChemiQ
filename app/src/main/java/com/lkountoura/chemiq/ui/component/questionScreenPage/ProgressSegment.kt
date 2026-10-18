package com.lkountoura.chemiq.ui.component.questionScreenPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressSegment(
    state: SegmentState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(6.dp)
            .background(color = state.status.color, shape = RoundedCornerShape(3.dp))
    )
}

@Immutable
data class SegmentState(
    val tag: String,
    private val _status: SegmentStatus = SegmentStatus.PENDING,
) {
    fun setSegmentState(
        status: SegmentStatus
    ): SegmentState = copy(
        _status = status
    )

    val status: SegmentStatus
        get() = _status
}

enum class SegmentStatus(
    val color: Color
) {
    CORRECT(Color(0xFF12A150)),
    WRONG(Color(0xFFE5484D)),
    CURRENT(Color(0xFF5B3DF5)),
    PENDING(Color(0xFFE8E6F0))
}