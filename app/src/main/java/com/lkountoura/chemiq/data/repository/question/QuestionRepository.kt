package com.lkountoura.chemiq.data.repository.question

import com.lkountoura.chemiq.converter.QuestionConverter
import com.lkountoura.chemiq.converter.dtoConverter.QuestionDTOConverter
import com.lkountoura.chemiq.data.dto.QuestionDTO
import com.lkountoura.chemiq.database.entities.ReviewQuestionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface QuestionRepository {
    suspend fun fetchQuestions()

    fun observeQuestions(
        unitId: Int,
        difficulty: Int
    ): Flow<List<QuestionDTO>>

    suspend fun storeWrongAnswer(questionId: Int)

    fun observeReviewQuestionsCount(): Flow<Int>
}

class QuestionRepositoryImpl @Inject constructor(
    val remoteDataSource: QuestionRemoteDataSource,
    val localDataSource: QuestionLocalDataSource,
) : QuestionRepository {

    override suspend fun fetchQuestions() {
        val responseList = remoteDataSource.fetchQuestions()

        val converter = QuestionConverter()

        val questionEntitiesList = converter.convert(responseList)

        localDataSource.storeQuestions(questionEntitiesList)
    }

    override fun observeQuestions(
        unitId: Int,
        difficulty: Int
    ): Flow<List<QuestionDTO>> {
        val dtoConverter = QuestionDTOConverter()

        return localDataSource.observeQuestions(
            unitId = unitId,
            difficulty = difficulty
        )
            .map { questions ->
                dtoConverter.convert(listOfQuestionEntities = questions)
            }
    }

    override suspend fun storeWrongAnswer(questionId: Int) {
        localDataSource.storeReviewQuestion(ReviewQuestionEntity(questionId = questionId))
    }

    override fun observeReviewQuestionsCount(): Flow<Int> {
        return localDataSource.observeReviewQuestionsCount()
    }
}