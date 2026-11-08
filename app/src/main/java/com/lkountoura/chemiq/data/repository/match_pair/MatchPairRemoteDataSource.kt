package com.lkountoura.chemiq.data.repository.match_pair

import android.content.Context
import com.lkountoura.chemiq.data.response.MatchPairResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface MatchPairRemoteDataSource {
    suspend fun fetchMatchPairs(): List<MatchPairResponse>

}

class MatchPairRemoteDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : MatchPairRemoteDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun fetchMatchPairs(): List<MatchPairResponse> {
        return withContext(Dispatchers.IO) {
            val raw = context.assets.open(MATCH_PAIRS_FILE).bufferedReader().use { it.readText() }
            json.decodeFromString<List<MatchPairResponse>>(raw)
        }
    }

    private companion object {
        const val MATCH_PAIRS_FILE = "match_pairs.json"
    }

}