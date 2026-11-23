package com.lkountoura.chemiq.ui.component.balancePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BalanceLabelRow(
    state: BalanceLabelRowState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "ΣΥΜΠΛΗΡΩΣΕ ΤΟΥΣ ΣΥΝΤΕΛΕΣΤΕΣ",
            modifier = Modifier.weight(1f),
            color = Color(0xFF2F7BF6),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = state.difficultyText,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(state.difficulty.backgroundColor)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            color = state.difficulty.textColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

data class BalanceLabelRowState(
    val difficultyText: String = "",
    val difficulty: BalanceDifficulty = BalanceDifficulty.EASY
)

enum class BalanceDifficulty(
    val backgroundColor: Color,
    val textColor: Color,
) {
    EASY(
        backgroundColor = Color(0xFFE3F7EC),
        textColor = Color(0xFF12A150),
    ),
    MEDIUM(
        backgroundColor = Color(0xFFFEF3DC),
        textColor = Color(0xFFD98A00),
    ),
    HARD(
        backgroundColor = Color(0xFFFDECEC),
        textColor = Color(0xFFE5484D),
    )
}