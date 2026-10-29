package com.lkountoura.chemiq.data.repository.question

import android.content.Context
import com.lkountoura.chemiq.data.response.QuestionResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface QuestionRemoteDataSource {
    suspend fun fetchQuestions(): List<QuestionResponse>
}

class QuestionRemoteDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : QuestionRemoteDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun fetchQuestions(): List<QuestionResponse> {
        return withContext(Dispatchers.IO) {
            val raw = context.assets.open(QUESTIONS_FILE).bufferedReader().use { it.readText() }
            json.decodeFromString<List<QuestionResponse>>(raw)
        }
    }

    private companion object {
        const val QUESTIONS_FILE = "questions.json"
    }

}
