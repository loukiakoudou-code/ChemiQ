package com.lkountoura.chemiq.data.repository.quizSelection

import com.lkountoura.chemiq.converter.QuizSelectionConverter
import com.lkountoura.chemiq.converter.dtoConverter.QuizSelectionDTOConverter
import com.lkountoura.chemiq.data.dto.QuizSelectionUnitDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface QuizSelectionsRepository {
    suspend fun fetchUnits()

    fun observeUnits(): Flow<List<QuizSelectionUnitDTO>>
}

class QuizSelectionsRepositoryImpl @Inject constructor(
    val remoteDataSource: QuizSelectionsRemoteDataSource,
    val localDataSource: QuizSelectionsLocalDataSource,
) : QuizSelectionsRepository {

    override suspend fun fetchUnits() {
        val responseList = remoteDataSource.fetchUnits()

        val converter = QuizSelectionConverter()

        val unitEntitiesList = converter.convert(responseList)

        localDataSource.storeUnits(unitEntitiesList)
    }

    override fun observeUnits(): Flow<List<QuizSelectionUnitDTO>> {
        val dtoConverter = QuizSelectionDTOConverter()

        return localDataSource.observeUnits()
            .map { units ->
                dtoConverter.convert(listOfUnitEntities = units)
            }
    }
}