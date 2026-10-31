package com.lkountoura.chemiq.data.repository.question

import com.lkountoura.chemiq.database.dao.QuestionDao
import com.lkountoura.chemiq.database.entities.GameResultEntity
import com.lkountoura.chemiq.database.entities.QuestionEntity
import com.lkountoura.chemiq.database.entities.ReviewQuestionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface QuestionLocalDataSource {
    suspend fun storeQuestions(questions: List<QuestionEntity>)

    suspend fun storeResults(results: GameResultEntity)

    fun observeQuestions(
        unitId: Int,
        difficulty: Int
    ): Flow<List<QuestionEntity>>

    suspend fun storeReviewQuestion(reviewQuestion: ReviewQuestionEntity)

    fun observeReviewQuestionsCount(): Flow<Int>

}

class QuestionLocalDataSourceImpl @Inject constructor(
    val dao: QuestionDao
) : QuestionLocalDataSource {
    override suspend fun storeQuestions(questions: List<QuestionEntity>) {
        withContext(Dispatchers.IO) {

            dao.storeQuestions(questions)
        }
    }

    override suspend fun storeResults(results: GameResultEntity) {
        withContext(Dispatchers.IO){
            dao.storeResults(results)
        }
    }

    override fun observeQuestions(
        unitId: Int,
        difficulty: Int
    ): Flow<List<QuestionEntity>> {
        return dao.observeQuestions(
            unitId = unitId,
            difficulty = difficulty
        )
    }

    override suspend fun storeReviewQuestion(reviewQuestion: ReviewQuestionEntity) {
        withContext(Dispatchers.IO) {
            dao.storeReviewQuestion(reviewQuestion)
        }
    }

    override fun observeReviewQuestionsCount(): Flow<Int> {
        return dao.observeReviewQuestionsCount()
    }
}
