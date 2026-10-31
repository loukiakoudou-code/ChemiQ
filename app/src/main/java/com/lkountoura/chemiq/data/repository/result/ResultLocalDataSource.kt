package com.lkountoura.chemiq.data.repository.result

import com.lkountoura.chemiq.database.dao.ResultDao
import com.lkountoura.chemiq.database.entities.GameResultEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ResultLocalDataSource {
    fun observeResults(): Flow<GameResultEntity>
}

class ResultLocalDataSourceImpl @Inject constructor(
    val dao: ResultDao
) : ResultLocalDataSource {
    override fun observeResults(): Flow<GameResultEntity> {
        return dao.observeResults()
    }
}