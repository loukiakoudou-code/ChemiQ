package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.common.ButtonState
import com.lkountoura.chemiq.ui.component.resultScreenPage.LevelCardState
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultStatPanelState
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultTitleState
import com.lkountoura.chemiq.ui.component.resultScreenPage.ScoreRingState

@Stable
sealed class ResultScreenState(
    open val resultStatPanelState: ResultStatPanelState? = null,
    open val levelCardState: LevelCardState? = null,
    open val chemiQButtonState: ButtonState? = null,
    open val scoreRingState: ScoreRingState? = null,
    open val resultTitleState: ResultTitleState? = null
) {

    data class Empty(
        override val resultStatPanelState: ResultStatPanelState? = null,
        override val levelCardState: LevelCardState? = null,
        override val chemiQButtonState: ButtonState? = null,
        override val scoreRingState: ScoreRingState? = null,
        override val resultTitleState: ResultTitleState? = null
    ) : ResultScreenState()

    data class Data(
        override val resultStatPanelState: ResultStatPanelState,
        override val levelCardState: LevelCardState,
        override val chemiQButtonState: ButtonState? = null,
        override val scoreRingState: ScoreRingState? = null,
        override val resultTitleState: ResultTitleState? = null
    ) : ResultScreenState(
        resultStatPanelState = resultStatPanelState,
        levelCardState = levelCardState,
        chemiQButtonState = chemiQButtonState,
        scoreRingState = scoreRingState,
        resultTitleState = resultTitleState
    )

}
