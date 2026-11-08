package com.lkountoura.chemiq.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lkountoura.chemiq.database.entities.MatchPairEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchPairDao {
    @Upsert
    fun storeMatchPairs(matchPairs: List<MatchPairEntity>)

    @Query("SELECT * FROM match_pair WHERE unitId = :unitId ORDER BY id")
    fun observeMatchPairs(unitId: Int): Flow<List<MatchPairEntity>>

}