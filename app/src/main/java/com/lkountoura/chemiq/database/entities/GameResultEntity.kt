package com.lkountoura.chemiq.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gameResult")
data class GameResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: Long?,
    val seri: Int,
    val correctAnswers: Int,
) {
    companion object {
        fun build(time: Long?, seri: Int, correctAnswers: Int): GameResultEntity =
            GameResultEntity(
                time = time,
                seri = seri,
                correctAnswers = correctAnswers
            )
    }
}