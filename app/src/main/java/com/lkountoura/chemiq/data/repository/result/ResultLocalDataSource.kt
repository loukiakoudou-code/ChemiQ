package com.lkountoura.chemiq.data.repository.result

import com.lkountoura.chemiq.database.dao.ResultDao
import com.lkountoura.chemiq.database.entities.GameResultEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ResultLocalDataSource {
    fun observeResults(): Flow<GameResultEntity?>

    suspend fun getLastXp(): Int?

    suspend fun getTotalXp(): Int
}

class ResultLocalDataSourceImpl @Inject constructor(
    val dao: ResultDao
) : ResultLocalDataSource {
    override fun observeResults(): Flow<GameResultEntity?> {
        return dao.observeResults()
    }

    override suspend fun getLastXp(): Int? {
        return withContext(Dispatchers.IO) {
            dao.getLastXp()
        }
    }

    override suspend fun getTotalXp(): Int {
        return withContext(Dispatchers.IO) {
            dao.getTotalXp()
        }
    }
}