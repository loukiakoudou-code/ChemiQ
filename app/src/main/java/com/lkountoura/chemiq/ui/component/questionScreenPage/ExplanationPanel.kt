package com.lkountoura.chemiq.ui.component.questionScreenPage


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R

@Composable
fun ExplanationPanel(
    state: ExplanationPanelState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFFF4DE), shape = RoundedCornerShape(16.dp))
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(state.icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = state.text,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF16132B),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = state.helpText,
                fontSize = 13.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF12A150),
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
        Text(
            text = state.description,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = Color(0xFF5E5A78),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Immutable
data class ExplanationPanelState(
    private val _text: String = "",
    private val _helpText: String = "",
    private val _description: String = "",
) {
    fun setExplanationPanelState(
        text: String,
        helpText: String,
        description: String
    ): ExplanationPanelState = copy(
        _text = text,
        _helpText = helpText,
        _description = description
    )

    val icon: Int
        get() = R.drawable.ic_lightbulb
    val text: String
        get() = _text
    val helpText: String
        get() = _helpText
    val description: String
        get() = _description
}

