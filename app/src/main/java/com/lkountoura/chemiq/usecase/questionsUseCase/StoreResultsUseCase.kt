package com.lkountoura.chemiq.usecase.questionsUseCase

import com.lkountoura.chemiq.data.repository.question.QuestionRepository
import javax.inject.Inject

interface StoreResultsUseCase {
    suspend fun execute(time: Long?, seri: Int, correctAnswers: Int)
}

class StoreResultsUseCaseImpl @Inject constructor(
    val repository: QuestionRepository
) : StoreResultsUseCase {

    override suspend fun execute(time: Long?, seri: Int, correctAnswers: Int) {
        repository.storeResults(time, seri, correctAnswers)
    }
}