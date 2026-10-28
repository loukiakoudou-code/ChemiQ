package com.lkountoura.chemiq.data.repository.quizSelection

import android.content.Context
import com.lkountoura.chemiq.data.response.UnitResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface QuizSelectionsRemoteDataSource {
    suspend fun fetchUnits(): List<UnitResponse>
}

class QuizSelectionsRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : QuizSelectionsRemoteDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun fetchUnits(): List<UnitResponse> {
        return withContext(Dispatchers.IO) {
            val raw = context.assets.open(UNITS_FILE).bufferedReader().use { it.readText() }
            json.decodeFromString<List<UnitResponse>>(raw)
        }
    }

    private companion object {
        const val UNITS_FILE = "units.json"
    }
}
