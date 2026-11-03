package com.lkountoura.chemiq.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.lkountoura.chemiq.database.entities.GameResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {
    @Query("SELECT * FROM gameResult ORDER BY id DESC LIMIT 1")
    fun observeResults(): Flow<GameResultEntity?>

    @Query("SELECT xpEarned FROM xp ORDER BY id DESC LIMIT 1")
    fun getLastXp(): Int?

    @Query("SELECT COALESCE(SUM(xpEarned), 0) FROM xp")
    fun getTotalXp(): Int
}