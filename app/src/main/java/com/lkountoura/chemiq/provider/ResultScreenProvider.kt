package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.data.dto.GameResultDTO
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultStatCardState
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultStatPanelState
import com.lkountoura.chemiq.ui.component.resultScreenPage.StatType
import com.lkountoura.chemiq.ui.state.ResultScreenState
import javax.inject.Inject

class ResultScreenProvider @Inject constructor() {

    fun provideEmpty(): ResultScreenState.Empty {
        return ResultScreenState.Empty()
    }

    fun provideData(result: GameResultDTO): ResultScreenState.Data {
        val time = result.time?.let { time ->
            time / 1000
        }

        val calculateTime = time?.let { time ->
            val hours = time / 3600
            val minutes = (time % 3600) / 60
            val seconds = time % 60
            "%02d:%02d:%02d".format(hours, minutes, seconds)
        }

        val tab1 = ResultStatCardState(
            result = "",
            title = "Κέρδος",
            statType = StatType.XP
        )
        val tab2 = ResultStatCardState(
            result = "${result.correctAnswers * 10}%",
            title = "Ακρίβεια",
            statType = StatType.ACCURACY
        )
        val tab3 = ResultStatCardState(
            result = calculateTime ?: "0",
            title = "Χρόνος",
            statType = StatType.TIME
        )
        return ResultScreenState.Data(
            resultStatPanelState = ResultStatPanelState(
                resultStatCard = listOf(tab1, tab2, tab3)
            )
        )
    }
}