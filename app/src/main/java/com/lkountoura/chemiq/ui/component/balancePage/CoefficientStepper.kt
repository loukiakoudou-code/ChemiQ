package com.lkountoura.chemiq.ui.component.balancePage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CoefficientStepper(
    state: CoefficientStepperState,
    modifier: Modifier = Modifier,
    events: CoefficientStepperEvents? = null,
) {
    val status = state.status
    val shape = RoundedCornerShape(50)
    val onMinus = events?.onMinusPressed
    val onPlus = events?.onPlusPressed

    Row(
        modifier = modifier
            .height(30.dp)
            .clip(shape)
            .background(status.backgroundColor)
            .border(status.borderWidth, status.borderColor, shape)
            .padding(horizontal = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(shape)
                .clickable(enabled = onMinus != null) { onMinus?.invoke(state.tag) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "−",
                color = status.signColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = state.value.toString(),
            color = status.valueColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(shape)
                .clickable(enabled = onPlus != null) { onPlus?.invoke(state.tag) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                color = status.signColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Immutable
data class CoefficientStepperState(
    val tag: String,
    private val _value: Int = 1,
    private val _status: CoefficientStepperStatus = CoefficientStepperStatus.DEFAULT,
) {
    fun setCoefficientStepperState(
        value: Int,
        status: CoefficientStepperStatus = CoefficientStepperStatus.DEFAULT
    ): CoefficientStepperState = copy(
        _value = value,
        _status = status
    )

    val value: Int
        get() = _value
    val status: CoefficientStepperStatus
        get() = _status
}

data class CoefficientStepperEvents(
    val onMinusPressed: ((tag: String) -> Unit)? = null,
    val onPlusPressed: ((tag: String) -> Unit)? = null,
)

enum class CoefficientStepperStatus(
    val backgroundColor: Color,
    val borderColor: Color,
    val borderWidth: Dp,
    val signColor: Color,
    val valueColor: Color,
) {
    DEFAULT(
        backgroundColor = Color(0xFFEFEEF5),
        borderColor = Color.Transparent,
        borderWidth = 0.dp,
        signColor = Color(0xFF5E5A78),
        valueColor = Color(0xFF16132B),
    ),
    SELECTED(
        backgroundColor = Color(0xFFE3EEFE),
        borderColor = Color(0xFF2F7BF6),
        borderWidth = 1.5.dp,
        signColor = Color(0xFF2F7BF6),
        valueColor = Color(0xFF2F7BF6),
    )
}