package com.lkountoura.chemiq.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "equation")
data class EquationEntity(
    @PrimaryKey
    val uuid: String,
    val id: Int,
    val unitId: Int,
    val difficulty: Int,
    val reactants: String,
    val products: String,
    val solution: String
) {
    companion object {
        fun build(
            id: Int,
            unitId: Int,
            difficulty: Int,
            reactants: String,
            products: String,
            solution: String
        ): EquationEntity = EquationEntity(
            uuid = UUID.nameUUIDFromBytes("$id:$reactants".toByteArray()).toString(),
            id = id,
            unitId = unitId,
            difficulty = difficulty,
            reactants = reactants,
            products = products,
            solution = solution
        )
    }
}