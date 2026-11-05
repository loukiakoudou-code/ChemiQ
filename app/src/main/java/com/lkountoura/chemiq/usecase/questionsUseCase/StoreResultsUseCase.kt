package com.lkountoura.chemiq.usecase.questionsUseCase

import com.lkountoura.chemiq.data.repository.question.QuestionRepository
import javax.inject.Inject

interface StoreResultsUseCase {
    suspend fun execute(
        time: Long?,
        seri: Int,
        correctAnswers: Int,
        game: String,
        unitTitle: String?,
        difficulty: Int? = null,
        answeredCount: Int = 0
    )
}

class StoreResultsUseCaseImpl @Inject constructor(
    val repository: QuestionRepository
) : StoreResultsUseCase {

    override suspend fun execute(
        time: Long?,
        seri: Int,
        correctAnswers: Int,
        game: String,
        unitTitle: String?,
        difficulty: Int?,
        answeredCount: Int
    ) {
        repository.storeResults(
            time,
            seri,
            correctAnswers,
            game,
            unitTitle,
            difficulty,
            answeredCount
        )
    }
}