package com.lkountoura.chemiq.ui.component.unitSelectionPage


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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

@Composable
fun DifficultyBox(
    state: DifficultyBoxState,
    modifier: Modifier = Modifier,
    events: DifficultyBoxEvents? = null,
) {
    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier = modifier
            .fillMaxHeight()
            .shadow(elevation = if (state.isSelected) 2.dp else 0.dp, shape = shape)
            .background(
                color = if (state.isSelected) Color.White else Color.Transparent, shape = shape
            )
            .clip(shape)
            .clickable { events?.onPressed?.invoke(state.tag) },
        contentAlignment = Alignment.Center

    )
    {
        Text(
            text = state.text,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (state.isSelected) Color(0xFF5B3DF5) else Color(0xFF5E5A78)
        )

    }

}

@Immutable
data class DifficultyBoxState(
    val tag: String,
    val text: String = "",
    val isSelected: Boolean = false,
) {
    fun setDifficultyBoxState(
        text: String,
        isSelected: Boolean = false
    ): DifficultyBoxState = copy(
        text = text,
        isSelected = isSelected
    )
}

data class DifficultyBoxEvents(
    val onPressed: ((tag: String) -> Unit)? = null
)