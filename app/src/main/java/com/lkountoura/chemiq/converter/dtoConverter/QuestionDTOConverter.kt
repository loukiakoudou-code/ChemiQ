package com.lkountoura.chemiq.converter.dtoConverter

import com.lkountoura.chemiq.data.dto.QuestionDTO
import com.lkountoura.chemiq.database.entities.QuestionEntity

class QuestionDTOConverter{

    fun convert(
        listOfQuestionEntities: List<QuestionEntity>
    ): List<QuestionDTO>{
        return listOfQuestionEntities.map { entity ->
            QuestionDTO(
                id = entity.id,
                unitId = entity.unitId,
                difficulty = entity.difficulty,
                text = entity.text,
                option1 = entity.option1,
                option2 = entity.option2,
                option3 = entity.option3,
                option4 = entity.option4,
                correct = entity.correct,
                explanation = entity.explanation
            )
        }
    }
}