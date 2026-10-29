package com.lkountoura.chemiq.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "question")
data class QuestionEntity(
    @PrimaryKey
    val uuid: String,
    val id: Int,
    val unitId: Int,
    val difficulty: Int,
    val text: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correct: Int,
    val explanation: String
) {
    companion object {

        fun build(
            id: Int,
            unitId: Int,
            difficulty: Int,
            text: String,
            option1: String,
            option2: String,
            option3: String,
            option4: String,
            correct: Int,
            explanation: String
        ): QuestionEntity = QuestionEntity(
            uuid = UUID.nameUUIDFromBytes("$id:$text".toByteArray()).toString(),
            id = id,
            unitId = unitId,
            difficulty = difficulty,
            text = text,
            option1 = option1,
            option2 = option2,
            option3 = option3,
            option4 = option4,
            correct = correct,
            explanation = explanation
        )
    }
}
