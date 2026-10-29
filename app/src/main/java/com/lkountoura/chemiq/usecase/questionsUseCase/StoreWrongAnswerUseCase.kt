package com.lkountoura.chemiq.usecase.questionsUseCase

import com.lkountoura.chemiq.data.repository.question.QuestionRepository
import javax.inject.Inject

interface StoreWrongAnswerUseCase {
    suspend fun execute(questionId: Int)
}

class StoreWrongAnswerUseCaseImpl @Inject constructor(
    private val repository: QuestionRepository,
) : StoreWrongAnswerUseCase {
    override suspend fun execute(questionId: Int) {
        repository.storeWrongAnswer(questionId)
    }
}
