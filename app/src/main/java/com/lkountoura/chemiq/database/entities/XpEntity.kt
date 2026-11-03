package com.lkountoura.chemiq.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "xp")
data class XpEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val xpEarned: Int,
) {
    companion object {
        fun build(xpEarned: Int): XpEntity =
            XpEntity(
                xpEarned = xpEarned
            )
    }
}
