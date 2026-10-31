package com.lkountoura.chemiq.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.lkountoura.chemiq.database.entities.GameResultEntity
import com.lkountoura.chemiq.database.entities.QuestionEntity
import com.lkountoura.chemiq.database.entities.ReviewQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Upsert
    fun storeQuestions(questions: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun storeReviewQuestion(reviewQuestion: ReviewQuestionEntity)

    @Upsert
    fun storeResults(results: GameResultEntity)

    @Query("SELECT * FROM question WHERE unitId = :unitId AND difficulty = :difficulty ORDER BY id")
    fun observeQuestions(unitId: Int, difficulty: Int): Flow<List<QuestionEntity>>

    @Query("SELECT COUNT(*) FROM review_question")
    fun observeReviewQuestionsCount(): Flow<Int>
}