package com.lkountoura.chemiq.ui.component.resultScreenPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val TextPrimary = Color(0xFF16132B)
private val TextSecondary = Color(0xFF5E5A78)

@Composable
fun ResultTitle(
    state: ResultTitleState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = state.title,
            color = TextPrimary,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )


        state.subtitle?.let { subtitle ->
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Immutable
data class ResultTitleState(
    val title: String = "",
    val subtitle: String? = null,
)