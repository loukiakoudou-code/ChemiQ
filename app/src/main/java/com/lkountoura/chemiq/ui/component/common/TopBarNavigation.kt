package com.lkountoura.chemiq.ui.component.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun TopBarNavigation(
    state: TopBarNavigationState,
    modifier: Modifier = Modifier,
    events: TopBarNavigationEvents? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { events?.onNavigationPressed?.invoke() },
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    painter = painterResource(state.navigationIcon.res),
                    contentDescription = null,
                )
            }
            Text(
                text = state.title,
                color = Color(0xFF16132B),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .background(state.chipColor.backgroundColor, RoundedCornerShape(50))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            state.chipIcon?.let { icon ->

                Icon(
                    painter = painterResource(icon.res),
                    contentDescription = null,
                    tint = icon.color,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = state.chipText,
                color = state.chipColor.textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Immutable
data class TopBarNavigationState(
    val navigationIcon: NavigationIcon,
    val title: String = "",
    val chipText: String,
    val chipIcon: ChipIcon? = null,
    val chipColor: ChipColor
)

data class TopBarNavigationEvents(
    val onNavigationPressed: (() -> Unit)? = null
)

enum class NavigationIcon(
    @param:DrawableRes val res: Int,
) {
    BACK(R.drawable.ic_arrow_left),
    CLOSE(R.drawable.ic_close),
}

enum class ChipIcon(
    @param:DrawableRes val res: Int,
    val color: Color,
) {
    FLASK(R.drawable.ic_flask_filled, Color(0xFF5B3DF5)),
    CHECK(R.drawable.ic_check, Color(0xFF12A150)),
}

enum class ChipColor(
    val backgroundColor: Color,
    val textColor: Color
) {
    PURPLE(backgroundColor = Color(0xFFEEEAFE), textColor = Color(0xFF5B3DF5)),
    GREEN(backgroundColor = Color(0xFFE3F7EC), textColor = Color(0xFF12A150)),
    TEAL(backgroundColor = Color(0xFFDDF6F4), textColor = Color(0xFF0EA5A0)),
    BLUE(backgroundColor = Color(0xFFE3EEFE), textColor = Color(0xFF2F7BF6)),
    PINK(backgroundColor = Color(0xFFFDE7F0), textColor = Color(0xFFE8478B)),

}












