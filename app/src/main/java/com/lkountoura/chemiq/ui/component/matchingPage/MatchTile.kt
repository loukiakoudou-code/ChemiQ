package com.lkountoura.chemiq.ui.component.matchingPage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R


@Composable
fun MatchTile(
    state: MatchTileState,
    modifier: Modifier = Modifier,
    events: MatchTileEvents? = null,
) {
    val onPressed = events?.onPressed
    val shape = RoundedCornerShape(20.dp)
    val status = state.status
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(shape)
            .background(status.backgroundColor)
            .border(status.borderWidth, status.borderColor, shape)
            .clickable(enabled = onPressed != null) { onPressed?.invoke(state.tag) }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = state.text,
            modifier = Modifier.weight(1f),
            color = status.textColor,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        status.icon?.let { icon ->
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = status.textColor,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Immutable
data class MatchTileState(
    val tag: String,
    private val _text: String = "",
    private val _status: MatchTileStatus = MatchTileStatus.DEFAULT,
) {
    fun setMatchTileState(
        text: String,
        status: MatchTileStatus = MatchTileStatus.DEFAULT
    ): MatchTileState = copy(
        _text = text,
        _status = status
    )

    val text: String
        get() = _text
    val status: MatchTileStatus
        get() = _status

}


data class MatchTileEvents(
    val onPressed: ((tag: String) -> Unit)? = null
)


enum class MatchTileStatus(
    val backgroundColor: Color,
    val borderColor: Color,
    val borderWidth: Dp,
    val textColor: Color,
    @param:DrawableRes val icon: Int? = null,
) {
    DEFAULT(
        backgroundColor = Color.White,
        borderColor = Color(0xFFE8E6F0),
        borderWidth = 1.5.dp,
        textColor = Color(0xFF16132B),
    ),
    CORRECT(
        backgroundColor = Color(0xFFE3F7EC),
        borderColor = Color(0xFF12A150),
        borderWidth = 2.dp,
        textColor = Color(0xFF12A150),
        icon = R.drawable.ic_check_circle,
    ),
    SELECTED(
        backgroundColor = Color(0xFFFDE7F0),
        borderColor = Color(0xFFE8478B),
        borderWidth = 2.dp,
        textColor = Color(0xFFE8478B),
    ),
    WRONG(
        backgroundColor = Color(0xFFFDECEC),
        borderColor = Color(0xFFE5484D),
        borderWidth = 2.dp,
        textColor = Color(0xFFE5484D),
    )
}