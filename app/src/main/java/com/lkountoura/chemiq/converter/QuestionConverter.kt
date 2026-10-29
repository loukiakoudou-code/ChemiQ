package com.lkountoura.chemiq.converter

import com.lkountoura.chemiq.data.response.QuestionResponse
import com.lkountoura.chemiq.database.entities.QuestionEntity

class QuestionConverter {
    fun convert(
        questionResponses: List<QuestionResponse>,
    ): List<QuestionEntity> {

        return questionResponses.map { response ->
            QuestionEntity.build(
                id = response.id,
                unitId = response.unitId,
                difficulty = response.difficulty,
                text = response.text,
                option1 = response.option1,
                option2 = response.option2,
                option3 = response.option3,
                option4 = response.option4,
                correct = response.correct,
                explanation = response.explanation
            )
        }
    }
}