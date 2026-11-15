package com.lkountoura.chemiq.data.repository.equation

import android.content.Context
import com.lkountoura.chemiq.data.response.EquationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface EquationRemoteDataSource {
    suspend fun fetchEquations(): List<EquationResponse>
}

class EquationRemoteDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : EquationRemoteDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun fetchEquations(): List<EquationResponse> {
        return withContext(Dispatchers.IO) {
            val raw = context.assets.open(EQUATIONS_FILE).bufferedReader().use { it.readText() }
            json.decodeFromString<List<EquationResponse>>(raw)
        }
    }

    private companion object {
        const val EQUATIONS_FILE = "equations.json"
    }
}
