package com.lkountoura.chemiq.ui.component.questionScreenPage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R

@Composable
fun QuestionTab(
    state: QuestionTabState,
    modifier: Modifier = Modifier,
    events: QuestionTabEvents? = null,
) {
    val onPressed = events?.onPressed
    val shape = RoundedCornerShape(20.dp)
    val status = state.status

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .alpha(status.alpha)
            .clip(shape)
            .background(status.backgroundColor)
            .border(status.borderWidth, status.borderColor, shape)
            .clickable(enabled = onPressed != null) { onPressed?.invoke(state.tag) }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(status.badgeColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.letter,
                color = status.letterColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = state.text,
            modifier = Modifier.weight(1f),
            color = status.textColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
        status.icon?.let { icon ->
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = status.iconColor,
                modifier = Modifier.size(22.dp)
            )
        }
    }


}


@Immutable
data class QuestionTabState(
    val tag: String,
    private val _letter: String = "",
    private val _text: String = "",
    private val _status: QuestionStatus = QuestionStatus.DEFAULT,
) {
    fun setQuestionTabState(
        letter: String,
        text: String,
        status: QuestionStatus = QuestionStatus.DEFAULT
    ): QuestionTabState = copy(
        _letter = letter,
        _text = text,
        _status = status
    )

    val letter: String
        get() = _letter
    val text: String
        get() = _text
    val status: QuestionStatus
        get() = _status
}

data class QuestionTabEvents(
    val onPressed: ((tag: String) -> Unit)? = null
)

enum class QuestionStatus(
    val backgroundColor: Color,
    val borderColor: Color,
    val borderWidth: Dp,
    val badgeColor: Color,
    val letterColor: Color,
    val textColor: Color,
    @param:DrawableRes val icon: Int? = null,
    val iconColor: Color = Color.Unspecified,
    val alpha: Float = 1f,
) {
    DEFAULT(
        backgroundColor = Color.White,
        borderColor = Color(0xFFE8E6F0),
        borderWidth = 1.5.dp,
        badgeColor = Color(0xFFF0EEF5),
        letterColor = Color(0xFF5E5A78),
        textColor = Color(0xFF16132B),
    ),
    CORRECT(
        backgroundColor = Color(0xFFE3F7EC),
        borderColor = Color(0xFF12A150),
        borderWidth = 2.dp,
        badgeColor = Color(0xFF12A150),
        letterColor = Color.White,
        textColor = Color(0xFF16132B),
        icon = R.drawable.ic_check_circle,
        iconColor = Color(0xFF12A150),
    ),
    WRONG(
        backgroundColor = Color(0xFFFDECEC),
        borderColor = Color(0xFFE5484D),
        borderWidth = 2.dp,
        badgeColor = Color(0xFFE5484D),
        letterColor = Color.White,
        textColor = Color(0xFF16132B),
        icon = R.drawable.ic_x_circle,
        iconColor = Color(0xFFE5484D),
    ),
    DIMMED(
        backgroundColor = Color.White,
        borderColor = Color(0xFFE8E6F0),
        borderWidth = 1.5.dp,
        badgeColor = Color(0xFFF0EEF5),
        letterColor = Color(0xFF9A97AE),
        textColor = Color(0xFF5E5A78),
        alpha = 0.5f,
    ),
}