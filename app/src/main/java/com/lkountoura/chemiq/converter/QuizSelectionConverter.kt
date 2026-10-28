package com.lkountoura.chemiq.converter

import UnitEntity
import com.lkountoura.chemiq.data.response.UnitResponse

class QuizSelectionConverter {

    fun convert(
        unitResponses: List<UnitResponse>,
    ) : List<UnitEntity> {

        return unitResponses.map { response ->
            UnitEntity.build(
                id = response.id,
                title = response.title,
                grade = response.grade,
                sortOrder = response.sortOrder,
            )
        }
    }
}