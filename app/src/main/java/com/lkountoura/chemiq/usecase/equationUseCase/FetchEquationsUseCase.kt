package com.lkountoura.chemiq.usecase.equationUseCase

import com.lkountoura.chemiq.data.repository.equation.EquationRepository
import javax.inject.Inject

interface FetchEquationsUseCase {
    suspend fun execute()
}

class FetchEquationsUseCaseImpl @Inject constructor(
    private val repository: EquationRepository,
) : FetchEquationsUseCase {
    override suspend fun execute() {
        repository.fetchEquations()
    }
}