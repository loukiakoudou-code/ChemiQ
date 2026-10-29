package com.lkountoura.chemiq.usecase.questionsUseCase

import com.lkountoura.chemiq.data.repository.question.QuestionRepository
import javax.inject.Inject

interface FetchQuestionsUseCase {
    suspend fun execute()
}

class StoreQuestionsUseCaseImpl @Inject constructor(
    private val repository: QuestionRepository,
) : FetchQuestionsUseCase {
    override suspend fun execute() {
        repository.fetchQuestions()
    }
}