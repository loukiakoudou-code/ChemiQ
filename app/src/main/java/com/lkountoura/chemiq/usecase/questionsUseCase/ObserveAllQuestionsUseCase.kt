package com.lkountoura.chemiq.usecase.questionsUseCase

import com.lkountoura.chemiq.data.dto.QuestionDTO
import com.lkountoura.chemiq.data.repository.question.QuestionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveAllQuestionsUseCase {
    fun execute(): Flow<List<QuestionDTO>>
}

class ObserveAllQuestionsUseCaseImpl @Inject constructor(
    val repository: QuestionRepository
) : ObserveAllQuestionsUseCase {
    override fun execute(): Flow<List<QuestionDTO>> {
        return repository.observeAllQuestions()
    }
}