package com.lkountoura.chemiq.viewmodel.helpers

import com.lkountoura.chemiq.data.dto.MatchPairDTO
import com.lkountoura.chemiq.xp.XpRules
import kotlin.random.Random

data class MatchingRound(
    val names: List<MatchPairDTO>,
    val formulas: List<MatchPairDTO>,
    val selectedNameId: Int? = null,
    val matchedIds: Set<Int> = emptySet(),
    val wrongNameId: Int? = null,
    val wrongFormulaId: Int? = null,
    val moves: Int = 0,
) {
    val isShowingWrong: Boolean
        get() = wrongNameId != null

    val isFinished: Boolean
        get() = matchedIds.size == names.size

    val mistakes: Int
        get() = moves - matchedIds.size

    val xpEarned: Int
        get() = XpRules.matchingTotal(matchedIds.size, mistakes)

    fun selectName(id: Int): MatchingRound {
        if (isShowingWrong || isFinished || id in matchedIds) return this
        return if (selectedNameId == id) {
            copy(selectedNameId = null)
        } else {
            copy(selectedNameId = id)
        }
    }

    fun selectFormula(id: Int): MatchingRound {
        val nameId = selectedNameId ?: return this
        if (isShowingWrong || isFinished || id in matchedIds) return this
        return if (id == nameId) {
            copy(
                matchedIds = matchedIds + id,
                selectedNameId = null,
                moves = moves + 1
            )
        } else {
            copy(
                wrongNameId = nameId,
                wrongFormulaId = id,
                selectedNameId = null,
                moves = moves + 1
            )
        }
    }

    fun clearWrong(): MatchingRound =
        copy(wrongNameId = null, wrongFormulaId = null)


    companion object {
        const val TOTAL_PAIRS = 5
        fun start(pool: List<MatchPairDTO>, random: Random = Random.Default): MatchingRound {
            val picked = pool.distinctBy { it.id }.shuffled(random).take(TOTAL_PAIRS)
            return MatchingRound(
                names = picked,
                formulas = picked.shuffled(random)
            )
        }
    }
}