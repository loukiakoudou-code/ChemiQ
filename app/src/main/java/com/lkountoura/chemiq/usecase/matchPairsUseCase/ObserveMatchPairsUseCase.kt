package com.lkountoura.chemiq.usecase.matchPairsUseCase

import com.lkountoura.chemiq.data.dto.MatchPairDTO
import com.lkountoura.chemiq.data.repository.match_pair.MatchPairRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveMatchPairsUseCase {
    fun execute(
        unitId: Int
    ): Flow<List<MatchPairDTO>>
}

class ObserveMatchPairsUseCaseImpl @Inject constructor(
    val repository: MatchPairRepository
) : ObserveMatchPairsUseCase {
    override fun execute(
        unitId: Int
    ): Flow<List<MatchPairDTO>> {
        return repository.observeMatchPairs(
            unitId = unitId
        )
    }
}
