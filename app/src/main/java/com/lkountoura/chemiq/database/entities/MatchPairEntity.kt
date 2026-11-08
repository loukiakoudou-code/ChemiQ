package com.lkountoura.chemiq.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "match_pair")
data class MatchPairEntity(
    @PrimaryKey
    val uuid: String,
    val id: Int,
    val unitId: Int,
    val name: String,
    val formula: String
) {
    companion object {
        fun build(
            id: Int,
            unitId: Int,
            name: String,
            formula: String
        ): MatchPairEntity = MatchPairEntity(
            uuid = UUID.nameUUIDFromBytes("$id:$name".toByteArray()).toString(),
            id = id,
            unitId = unitId,
            name = name,
            formula = formula
        )
    }
}

