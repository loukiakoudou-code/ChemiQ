package com.lkountoura.chemiq.database

import UnitEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import com.lkountoura.chemiq.database.dao.QuestionDao
import com.lkountoura.chemiq.database.dao.QuizSelectionsDao
import com.lkountoura.chemiq.database.dao.ResultDao
import com.lkountoura.chemiq.database.entities.GameResultEntity
import com.lkountoura.chemiq.database.entities.QuestionEntity
import com.lkountoura.chemiq.database.entities.ReviewQuestionEntity

@Database(
    entities = [
        UnitEntity::class,
        QuestionEntity::class,
        ReviewQuestionEntity::class,
        GameResultEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class ChemiqDatabase : RoomDatabase() {
    abstract fun unitDao(): QuizSelectionsDao
    abstract fun questionDao(): QuestionDao
    abstract fun resultDao(): ResultDao

}