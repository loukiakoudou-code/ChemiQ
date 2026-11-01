package com.lkountoura.chemiq.ui.state

import androidx.compose.runtime.Stable
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultStatPanelState

@Stable
sealed class ResultScreenState(
    open val resultStatPanelState: ResultStatPanelState? = null
) {

    data class Empty(
        override val resultStatPanelState: ResultStatPanelState? = null
    ) : ResultScreenState()

    data class Data(
        override val resultStatPanelState: ResultStatPanelState
    ) : ResultScreenState(
        resultStatPanelState = resultStatPanelState
    )
}
