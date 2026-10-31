package com.lkountoura.chemiq.database

import android.content.Context
import androidx.room.Room
import com.lkountoura.chemiq.database.dao.QuestionDao
import com.lkountoura.chemiq.database.dao.QuizSelectionsDao
import com.lkountoura.chemiq.database.dao.ResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ChemiqDatabase =
        Room.databaseBuilder(context, ChemiqDatabase::class.java, "chemiq.db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides
    fun provideQuizSelectionsDao(db: ChemiqDatabase): QuizSelectionsDao = db.unitDao()

    @Provides
    fun provideQuestionDao(db: ChemiqDatabase): QuestionDao = db.questionDao()

    @Provides
    fun provideResultDao(db: ChemiqDatabase): ResultDao = db.resultDao()
}