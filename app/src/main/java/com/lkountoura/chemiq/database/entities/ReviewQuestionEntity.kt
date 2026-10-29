package com.lkountoura.chemiq.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Keyed by the stable question id, not QuestionEntity.uuid, which changes when the question text changes.
@Entity(tableName = "review_question")
data class ReviewQuestionEntity(
    @PrimaryKey
    val questionId: Int,
)
