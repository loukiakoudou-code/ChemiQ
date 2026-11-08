package com.lkountoura.chemiq.data.repository.match_pair

import com.lkountoura.chemiq.database.dao.MatchPairDao
import com.lkountoura.chemiq.database.entities.MatchPairEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MatchPairLocalDataSource {
    suspend fun storeMatchPairs(matchPairs: List<MatchPairEntity>)

    fun observeMatchPairs(unitId: Int): Flow<List<MatchPairEntity>>

}

class MatchPairLocalDataSourceImpl @Inject constructor(
    val dao: MatchPairDao
) : MatchPairLocalDataSource {
    override suspend fun storeMatchPairs(matchPairs: List<MatchPairEntity>) {
        withContext(Dispatchers.IO) {
            dao.storeMatchPairs(matchPairs)
        }
    }

    override fun observeMatchPairs(unitId: Int): Flow<List<MatchPairEntity>> {
        return dao.observeMatchPairs(unitId)
    }

}