package com.lkountoura.chemiq.data.repository.result

import com.lkountoura.chemiq.converter.dtoConverter.ResultDTOConverter
import com.lkountoura.chemiq.data.dto.GameResultDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ResultRepository {
    fun observeResults(): Flow<GameResultDTO>
}

class ResultRepositoryImpl @Inject constructor(
    val localDataSource: ResultLocalDataSource
) : ResultRepository {

    override fun observeResults(): Flow<GameResultDTO> {

        val dtoConverter = ResultDTOConverter()

        return localDataSource.observeResults().map { entity ->
            dtoConverter.convert(entity)
        }
    }
}