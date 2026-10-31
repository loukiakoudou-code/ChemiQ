package com.lkountoura.chemiq.usecase.resultUseCase

import com.lkountoura.chemiq.data.dto.GameResultDTO
import com.lkountoura.chemiq.data.repository.result.ResultRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveResultUseCase {
    fun execute(): Flow<GameResultDTO>
}

class ObserveResultUseCaseImpl @Inject constructor(
    val repository: ResultRepository
) : ObserveResultUseCase {
    override fun execute(): Flow<GameResultDTO> {
        return repository.observeResults()
    }
}
