package com.lkountoura.chemiq.converter

import com.lkountoura.chemiq.data.response.MatchPairResponse
import com.lkountoura.chemiq.database.entities.MatchPairEntity

class MatchPairConverter {
    fun convert(
        matchPairResponses: List<MatchPairResponse>
    ): List<MatchPairEntity> {
        return matchPairResponses.map { response ->
            MatchPairEntity.build(
                id = response.id,
                unitId = response.unitId,
                name = response.name,
                formula = response.formula
            )
        }
    }
}