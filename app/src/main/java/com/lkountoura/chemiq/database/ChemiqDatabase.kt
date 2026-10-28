package com.lkountoura.chemiq.database

import UnitEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import com.lkountoura.chemiq.database.dao.QuizSelectionsDao

@Database(
    entities = [
        UnitEntity::class,
//        QuestionEntity::class,
//        AnswerEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class ChemiqDatabase : RoomDatabase() {
    abstract fun unitDao(): QuizSelectionsDao
//    abstract fun questionDao(): QuestionDao
//    abstract fun answerDao(): AnswerDao
}