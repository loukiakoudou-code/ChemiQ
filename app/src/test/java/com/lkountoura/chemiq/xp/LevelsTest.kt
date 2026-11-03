package com.lkountoura.chemiq.xp

import org.junit.Assert.assertEquals
import org.junit.Test

class LevelsTest {

    @Test
    fun startOf_fifthLevelStartsAt500() {
        assertEquals(500, Levels.startOf(5))
    }

    @Test
    fun levelFor_zeroXpIsTheFirstLevel() {
        assertEquals(1, Levels.levelFor(0))
    }

    @Test
    fun levelFor_movesToTheSecondLevelAt50() {
        assertEquals(1, Levels.levelFor(49))
        assertEquals(2, Levels.levelFor(50))
    }

    @Test
    fun levelFor_findsTheLevelThatContainsTheXp() {
        assertEquals(4, Levels.levelFor(320))
        assertEquals(5, Levels.levelFor(500))
    }

    @Test
    fun progressInLevel_isMeasuredInsideTheCurrentLevel() {
        assertEquals(0.1f, Levels.progressInLevel(320), 0.0001f)
    }
}
