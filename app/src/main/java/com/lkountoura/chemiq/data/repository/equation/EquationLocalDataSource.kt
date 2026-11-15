package com.lkountoura.chemiq.data.repository.equation

import com.lkountoura.chemiq.database.dao.EquationDao
import com.lkountoura.chemiq.database.entities.EquationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface EquationLocalDataSource {
    suspend fun storeEquations(equations: List<EquationEntity>)

    fun observeEquations(difficulty: Int): Flow<List<EquationEntity>>
}

class EquationLocalDataSourceImpl @Inject constructor(
    val dao: EquationDao
) : EquationLocalDataSource {
    override suspend fun storeEquations(equations: List<EquationEntity>) {
        withContext(Dispatchers.IO) {
            dao.storeEquations(equations)
        }
    }

    override fun observeEquations(difficulty: Int): Flow<List<EquationEntity>> {
        return dao.observeEquations(difficulty)
    }
}
