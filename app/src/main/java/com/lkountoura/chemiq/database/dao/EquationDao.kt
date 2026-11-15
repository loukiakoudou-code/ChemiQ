package com.lkountoura.chemiq.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lkountoura.chemiq.database.entities.EquationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EquationDao {
    @Upsert
    fun storeEquations(equations: List<EquationEntity>)

    @Query("SELECT * FROM equation WHERE difficulty = :difficulty ORDER BY id")
    fun observeEquations(difficulty: Int): Flow<List<EquationEntity>>
}