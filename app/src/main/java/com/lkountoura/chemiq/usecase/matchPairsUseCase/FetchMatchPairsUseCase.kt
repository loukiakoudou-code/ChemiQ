package com.lkountoura.chemiq.usecase.matchPairsUseCase

import com.lkountoura.chemiq.data.repository.match_pair.MatchPairRepository
import javax.inject.Inject

interface FetchMatchPairsUseCase {
    suspend fun execute()
}

class FetchMatchPairsUseCaseImpl @Inject constructor(
    private val repository: MatchPairRepository,
) : FetchMatchPairsUseCase {
    override suspend fun execute() {
        repository.fetchMatchPairs()
    }
}