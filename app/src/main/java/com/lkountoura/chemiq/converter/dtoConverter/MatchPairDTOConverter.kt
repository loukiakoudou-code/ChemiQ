package com.lkountoura.chemiq.converter.dtoConverter

import com.lkountoura.chemiq.data.dto.MatchPairDTO
import com.lkountoura.chemiq.database.entities.MatchPairEntity

class MatchPairDTOConverter {
    fun convert(
        listOfMatchPairEntities: List<MatchPairEntity>
    ): List<MatchPairDTO> {
        return listOfMatchPairEntities.map { entity ->
            MatchPairDTO(
                id = entity.id,
                unitId = entity.unitId,
                name = entity.name,
                formula = entity.formula
            )
        }
    }
}
