package com.lkountoura.chemiq.usecase.equationUseCase

import com.lkountoura.chemiq.data.dto.EquationDTO
import com.lkountoura.chemiq.data.repository.equation.EquationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveEquationsUseCase {
    fun execute(
        difficulty: Int
    ): Flow<List<EquationDTO>>
}

class ObserveEquationsUseCaseImpl @Inject constructor(
    val repository: EquationRepository
) : ObserveEquationsUseCase {
    override fun execute(
        difficulty: Int
    ): Flow<List<EquationDTO>> {
        return repository.observeEquations(
            difficulty = difficulty
        )
    }
}
