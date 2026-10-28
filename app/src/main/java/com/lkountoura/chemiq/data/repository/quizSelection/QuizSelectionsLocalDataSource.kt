package com.lkountoura.chemiq.data.repository.quizSelection

import UnitEntity
import com.lkountoura.chemiq.database.dao.QuizSelectionsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface QuizSelectionsLocalDataSource {
    suspend fun storeUnits(units: List<UnitEntity>)

    fun observeUnits(): Flow<List<UnitEntity>>
}

class QuizSelectionsLocalDataSourceImpl @Inject constructor(
    val dao: QuizSelectionsDao
) : QuizSelectionsLocalDataSource {

    override suspend fun storeUnits(units: List<UnitEntity>) {
        withContext(Dispatchers.IO) {
            dao.storeUnits(units)
        }
    }

    override fun observeUnits(): Flow<List<UnitEntity>> {
        return dao.observeUnits()
    }
}
