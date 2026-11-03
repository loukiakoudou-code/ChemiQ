package com.lkountoura.chemiq.usecase.resultUseCase

import com.lkountoura.chemiq.data.dto.XpDTO
import com.lkountoura.chemiq.data.repository.result.ResultRepository
import javax.inject.Inject

interface GetXpUseCase {
    suspend fun execute(): XpDTO
}

class GetXpUseCaseImpl @Inject constructor(
    val repository: ResultRepository
) : GetXpUseCase {
    override suspend fun execute(): XpDTO {
        return repository.getXp()
    }
}
