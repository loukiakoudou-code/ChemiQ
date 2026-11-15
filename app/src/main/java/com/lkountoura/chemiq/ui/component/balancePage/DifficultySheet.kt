package com.lkountoura.chemiq.ui.component.balancePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.lkountoura.chemiq.ui.component.common.ButtonActionsEvents
import com.lkountoura.chemiq.ui.component.common.ButtonState
import com.lkountoura.chemiq.ui.component.common.ChemiQButton
import com.lkountoura.chemiq.ui.component.unitSelectionPage.DifficultyPanel
import com.lkountoura.chemiq.ui.component.unitSelectionPage.DifficultyPanelEvents
import com.lkountoura.chemiq.ui.component.unitSelectionPage.DifficultyPanelState

@Composable
fun DifficultySheet(
    state: DifficultySheetState,
    modifier: Modifier = Modifier,
    events: DifficultySheetEvents? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFE3EEFE), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_scale),
                    contentDescription = null,
                    tint = Color(0xFF2F7BF6),
                    modifier = Modifier.size(26.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = state.title,
                    fontSize = 20.sp,
                    lineHeight = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF16132B)
                )
                Text(
                    text = state.subtitle,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = Color(0xFF5E5A78)
                )
            }
        }
        DifficultyPanel(
            state = state.difficultyPanelState,
            events = DifficultyPanelEvents(
                onPressed = events?.onDifficultyPressed
            )
        )
        LevelInfoCard(state = state.levelInfoCardState)

        ChemiQButton(
            state = state.buttonState,
            events = ButtonActionsEvents(
                onMainButtonPressed = events?.onStartPressed
            ),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}


@Immutable
data class DifficultySheetState(
    val title: String,
    val subtitle: String,
    val difficultyPanelState: DifficultyPanelState = DifficultyPanelState(),
    val levelInfoCardState: LevelInfoCardState = LevelInfoCardState(),
    val buttonState: ButtonState
)

data class DifficultySheetEvents(
    val onDifficultyPressed: ((tag: String) -> Unit)? = null,
    val onStartPressed: ((tag: String) -> Unit)? = null,
)
