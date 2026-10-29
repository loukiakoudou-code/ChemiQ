package com.lkountoura.chemiq.usecase.quizSelectionsUseCase

import com.lkountoura.chemiq.data.dto.QuizSelectionUnitDTO
import com.lkountoura.chemiq.data.repository.quizSelection.QuizSelectionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveUnitsUseCase {

    fun execute(): Flow<List<QuizSelectionUnitDTO>>
}

class ObserveUnitsUseCaseImpl @Inject constructor(
    val repository: QuizSelectionsRepository
) : ObserveUnitsUseCase {

    override fun execute(): Flow<List<QuizSelectionUnitDTO>> {
        return repository.observeUnits()
    }
}
