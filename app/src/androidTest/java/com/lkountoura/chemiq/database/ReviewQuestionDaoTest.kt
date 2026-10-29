package com.lkountoura.chemiq.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lkountoura.chemiq.database.dao.QuestionDao
import com.lkountoura.chemiq.database.entities.ReviewQuestionEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReviewQuestionDaoTest {

    private lateinit var database: ChemiqDatabase
    private lateinit var dao: QuestionDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            ChemiqDatabase::class.java
        ).build()
        dao = database.questionDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun emptyCollection_countsZero() = runBlocking {
        assertEquals(0, dao.observeReviewQuestionsCount().first())
    }

    @Test
    fun theSameQuestionAnsweredWrongTwice_isStoredOnce() = runBlocking {
        dao.storeReviewQuestion(ReviewQuestionEntity(questionId = 101))
        dao.storeReviewQuestion(ReviewQuestionEntity(questionId = 101))

        assertEquals(1, dao.observeReviewQuestionsCount().first())
    }

    @Test
    fun differentQuestions_areCountedSeparately() = runBlocking {
        dao.storeReviewQuestion(ReviewQuestionEntity(questionId = 101))
        dao.storeReviewQuestion(ReviewQuestionEntity(questionId = 205))

        assertEquals(2, dao.observeReviewQuestionsCount().first())
    }
}
