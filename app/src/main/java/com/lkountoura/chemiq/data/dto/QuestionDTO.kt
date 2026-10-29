package com.lkountoura.chemiq.data.dto

data class QuestionDTO(
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
)