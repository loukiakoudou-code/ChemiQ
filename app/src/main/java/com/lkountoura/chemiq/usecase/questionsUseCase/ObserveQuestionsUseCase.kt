package com.lkountoura.chemiq.usecase.questionsUseCase

import com.lkountoura.chemiq.data.dto.QuestionDTO
import com.lkountoura.chemiq.data.repository.question.QuestionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveQuestionsUseCase {

    fun execute(
        unitId: Int,
        difficulty: Int
    ): Flow<List<QuestionDTO>>
}

class ObserveQuestionsUseCaseImpl @Inject constructor(
    val repository: QuestionRepository
) : ObserveQuestionsUseCase {

    override fun execute(
        unitId: Int,
        difficulty: Int
    ): Flow<List<QuestionDTO>> {
        return repository.observeQuestions(
            unitId = unitId,
            difficulty = difficulty
        )
    }
}