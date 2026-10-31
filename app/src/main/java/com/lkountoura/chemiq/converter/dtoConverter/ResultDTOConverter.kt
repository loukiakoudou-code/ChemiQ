package com.lkountoura.chemiq.converter.dtoConverter

import com.lkountoura.chemiq.data.dto.GameResultDTO
import com.lkountoura.chemiq.database.entities.GameResultEntity

class ResultDTOConverter {

    fun convert(
        entity: GameResultEntity
    ): GameResultDTO {
        return GameResultDTO(
            time = entity.time,
            seri = entity.seri,
            correctAnswers = entity.correctAnswers
        )
    }
}

