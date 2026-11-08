package com.lkountoura.chemiq.data.repository.match_pair

import com.lkountoura.chemiq.converter.MatchPairConverter
import com.lkountoura.chemiq.converter.dtoConverter.MatchPairDTOConverter
import com.lkountoura.chemiq.data.dto.MatchPairDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface MatchPairRepository {
    suspend fun fetchMatchPairs()

    fun observeMatchPairs(
        unitId: Int
    ): Flow<List<MatchPairDTO>>
}

class MatchPairRepositoryImpl @Inject constructor(
    val remoteDataSource: MatchPairRemoteDataSource,
    val localDataSource: MatchPairLocalDataSource,
) : MatchPairRepository {

    override suspend fun fetchMatchPairs() {
        val responseList = remoteDataSource.fetchMatchPairs()
        val converter = MatchPairConverter()
        val entities = converter.convert(responseList)
        localDataSource.storeMatchPairs(entities)
    }

    override fun observeMatchPairs(unitId: Int): Flow<List<MatchPairDTO>> {
        val dtoConverter = MatchPairDTOConverter()
        return localDataSource.observeMatchPairs(unitId)
            .map { entities ->
                dtoConverter.convert(listOfMatchPairEntities = entities)
            }
    }
}