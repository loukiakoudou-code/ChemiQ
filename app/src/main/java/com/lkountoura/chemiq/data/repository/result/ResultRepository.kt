package com.lkountoura.chemiq.data.repository.result

import com.lkountoura.chemiq.converter.dtoConverter.ResultDTOConverter
import com.lkountoura.chemiq.data.dto.GameResultDTO
import com.lkountoura.chemiq.data.dto.XpDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ResultRepository {
    fun observeResults(): Flow<GameResultDTO>

    suspend fun getXp(): XpDTO
}

class ResultRepositoryImpl @Inject constructor(
    val localDataSource: ResultLocalDataSource
) : ResultRepository {

    override fun observeResults(): Flow<GameResultDTO> {

        val dtoConverter = ResultDTOConverter()

        return localDataSource.observeResults().filterNotNull().map { entity ->
            dtoConverter.convert(entity)
        }
    }

    override suspend fun getXp(): XpDTO {
        return XpDTO(
            xpEarned = localDataSource.getLastXp() ?: 0,
            totalXp = localDataSource.getTotalXp()
        )
    }
}