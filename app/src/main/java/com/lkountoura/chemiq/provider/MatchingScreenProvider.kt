package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.ui.component.common.ChipColor
import com.lkountoura.chemiq.ui.component.common.NavigationIcon
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.matchingPage.MatchPanelState
import com.lkountoura.chemiq.ui.component.matchingPage.MatchProgressCardState
import com.lkountoura.chemiq.ui.component.matchingPage.MatchTileState
import com.lkountoura.chemiq.ui.component.matchingPage.MatchTileStatus
import com.lkountoura.chemiq.ui.state.MatchingScreenState
import com.lkountoura.chemiq.viewmodel.helpers.MatchingRound
import javax.inject.Inject


class MatchingScreenProvider @Inject constructor() {
    fun provideEmpty(): MatchingScreenState = MatchingScreenState.Empty

    fun provideData(round: MatchingRound? = null): MatchingScreenState {
        val nameTiles = round?.names?.map { dto ->
            MatchTileState(tag = dto.id.toString()).setMatchTileState(
                text = dto.name,
                status = provideNameStatus(id = dto.id, round)
            )
        } ?: emptyList()
        val formulaTiles = round?.formulas?.map { dto ->
            MatchTileState(tag = dto.id.toString()).setMatchTileState(
                text = toSubscript(dto.formula),
                status = provideFormulaStatus(id = dto.id, round)
            )
        } ?: emptyList()
        val topBarNavigationState = TopBarNavigationState(
            navigationIcon = NavigationIcon.CLOSE,
            title = "Αντιστοίχιση",
            chipText = "Κινήσεις: ${round?.moves ?: 0}",
            chipColor = ChipColor.PINK
        )
        return MatchingScreenState.Data(
            topBarNavigationState = topBarNavigationState,
            matchPanelState = MatchPanelState(
                names = nameTiles,
                formulas = formulaTiles,
                isLocked = round?.isShowingWrong == true || round?.isFinished == true
            ),
            matchProgressCardState = MatchProgressCardState(
                matched = round?.matchedIds?.size ?: 0,
                total = round?.names?.size ?: 0
            )
        )
    }

    private fun provideNameStatus(id: Int, round: MatchingRound?): MatchTileStatus {
        return when {
            round?.selectedNameId == id -> MatchTileStatus.SELECTED
            round?.matchedIds?.contains(id) == true -> MatchTileStatus.CORRECT
            round?.wrongNameId == id -> MatchTileStatus.WRONG
            else -> MatchTileStatus.DEFAULT
        }
    }

    private fun provideFormulaStatus(id: Int, round: MatchingRound?): MatchTileStatus {
        return when {
            round?.matchedIds?.contains(id) == true -> MatchTileStatus.CORRECT
            round?.wrongFormulaId == id -> MatchTileStatus.WRONG
            else -> MatchTileStatus.DEFAULT
        }
    }

    private fun toSubscript(formula: String): String {
        val subscripts = "₀₁₂₃₄₅₆₇₈₉"
        return formula.map { char ->
            if (char.isDigit()) subscripts[char.digitToInt()] else char
        }.joinToString("")
    }
}
