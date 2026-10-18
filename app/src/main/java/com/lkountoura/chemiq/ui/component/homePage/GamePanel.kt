package com.lkountoura.chemiq.ui.component.homePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lkountoura.chemiq.R

@Composable
fun GamePanel(
    state: GamePanelState,
    modifier: Modifier = Modifier,
    events: GamePanelEvents? = null,
) {
    val tabEvents = GameTabEvents(onPressed = events?.onPressed)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        state.text?.let { text ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                state.helpText?.let { helpText ->
                    Text(text = helpText)
                }
            }
        }
    }

    state.gameTabs.chunked(2).forEach { rowTabs ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            rowTabs.forEach { tab ->
                GameTab(
                    state = tab,
                    events = tabEvents,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Immutable
data class GamePanelState(
    val text: String? = null,
    val helpText: String? = null,
    val gameTabs: List<GameTabState> = emptyList()
) {
    fun setGamePanelState(gameTabs: List<GameTabState>): GamePanelState =
        copy(gameTabs = gameTabs)
}

data class GamePanelEvents(
    val onPressed: ((tag: String) -> Unit)? = null
)

@Preview(showBackground = true, backgroundColor = 0xFFF7F6FB)
@Composable
private fun GamePanelPreview() {
    Box(Modifier.padding(20.dp)) {
        GamePanel(
            state = GamePanelState(
                gameTabs = listOf(
                    GameTabState(tag = "quiz").setGameState(
                        title = "Quiz",
                        description = "10 ερωτήσεις ανά ενότητα",
                        boxColor = Color(0xFFEEEAFE),
                        iconColor = Color(0xFF5B3DF5),
                        icon = R.drawable.ic_flask,
                    ),
                    GameTabState(tag = "time_attack").setGameState(
                        title = "Time Attack",
                        description = "60 δευτερόλεπτα",
                        boxColor = Color(0xFFFFEDDF),
                        iconColor = Color(0xFFF97316),
                        icon = R.drawable.ic_timer,
                    ),
                    GameTabState(tag = "match").setGameState(
                        title = "Αντιστοίχιση",
                        description = "Ονόματα και χημικοί τύποι",
                        boxColor = Color(0xFFFDE7F0),
                        iconColor = Color(0xFFE8478B),
                        icon = R.drawable.ic_link,
                    ),
                )
            )
        )
    }
}