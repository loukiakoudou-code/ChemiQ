package com.lkountoura.chemiq.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.lkountoura.chemiq.database.entities.GameResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {
    @Query("SELECT MAX(id) FROM gameResult")
    fun observeResults(): Flow<GameResultEntity>
}