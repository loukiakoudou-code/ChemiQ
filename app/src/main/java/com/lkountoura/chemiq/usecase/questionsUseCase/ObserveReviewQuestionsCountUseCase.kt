package com.lkountoura.chemiq.usecase.questionsUseCase

import com.lkountoura.chemiq.data.repository.question.QuestionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveReviewQuestionsCountUseCase {
    fun execute(): Flow<Int>
}

class ObserveReviewQuestionsCountUseCaseImpl @Inject constructor(
    private val repository: QuestionRepository,
) : ObserveReviewQuestionsCountUseCase {
    override fun execute(): Flow<Int> {
        return repository.observeReviewQuestionsCount()
    }
}
