package com.lkountoura.chemiq.usecase.questionsUseCase

import com.lkountoura.chemiq.data.repository.question.QuestionRepository
import javax.inject.Inject

interface StoreXpUseCase {
    suspend fun execute(xpEarned: Int)
}

class StoreXpUseCaseImpl @Inject constructor(
    val repository: QuestionRepository
) : StoreXpUseCase {

    override suspend fun execute(xpEarned: Int) {
        repository.storeXp(xpEarned)
    }
}
