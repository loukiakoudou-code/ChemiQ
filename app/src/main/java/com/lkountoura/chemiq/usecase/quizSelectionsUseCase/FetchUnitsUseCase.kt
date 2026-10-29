package com.lkountoura.chemiq.usecase.quizSelectionsUseCase

import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRepository
import javax.inject.Inject

interface FetchUnitsUseCase {
    suspend fun execute()
}

class StoreUnitsUseCaseImpl @Inject constructor(
    private val repository: QuizSelectionsRepository,
) : FetchUnitsUseCase {

    // Συχνά είναι τόσο λεπτό. Δεν πειράζει: δίνει όνομα στη δουλειά.
    override suspend fun execute() {
        repository.fetchUnits()
    }

}