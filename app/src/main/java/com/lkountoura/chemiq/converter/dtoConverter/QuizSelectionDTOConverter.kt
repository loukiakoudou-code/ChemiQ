package com.lkountoura.chemiq.converter.dtoConverter

import UnitEntity
import com.lkountoura.chemiq.data.dto.QuizSelectionUnitDTO

class QuizSelectionDTOConverter {

    fun convert(
        listOfUnitEntities: List<UnitEntity>,
    ): List<QuizSelectionUnitDTO> {

        return listOfUnitEntities.map { entity ->
            QuizSelectionUnitDTO(
                id = entity.id,
                title = entity.title,
                grade = entity.grade,
                sortOrder = entity.sortOrder,
            )
        }
    }
}