package com.lkountoura.chemiq.data.repository.equation

import com.lkountoura.chemiq.converter.EquationConverter
import com.lkountoura.chemiq.converter.dtoConverter.EquationDTOConverter
import com.lkountoura.chemiq.data.dto.EquationDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface EquationRepository {
    suspend fun fetchEquations()
    fun observeEquations(
        difficulty: Int
    ): Flow<List<EquationDTO>>
}

class EquationRepositoryImpl @Inject constructor(
    val remoteDataSource: EquationRemoteDataSource,
    val localDataSource: EquationLocalDataSource,
) : EquationRepository {
    override suspend fun fetchEquations() {
        val responseList = remoteDataSource.fetchEquations()
        val converter = EquationConverter()
        val entities = converter.convert(responseList)
        localDataSource.storeEquations(entities)
    }

    override fun observeEquations(difficulty: Int): Flow<List<EquationDTO>> {
        val dtoConverter = EquationDTOConverter()
        return localDataSource.observeEquations(difficulty)
            .map { entities ->
                dtoConverter.convert(listOfEquationEntities = entities)
            }
    }
}
