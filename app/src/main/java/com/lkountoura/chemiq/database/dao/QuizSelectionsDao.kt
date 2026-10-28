package com.lkountoura.chemiq.database.dao

import UnitEntity
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizSelectionsDao {

    @Upsert
    fun storeUnits(units: List<UnitEntity>)

    @Query("SELECT * FROM units")
    fun observeUnits(): Flow<List<UnitEntity>>
}