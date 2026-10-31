package com.lkountoura.chemiq.viewmodel.helpers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ScreenDwellAccumulatorTest {

    @Test
    fun finish_excludesTimeSpentInTheBackground() {
        val dwell = ScreenDwellAccumulator()

        dwell.onForeground(0)
        dwell.onBackground(5_000)
        dwell.onForeground(60_000)

        assertEquals(7_000L, dwell.finish(62_000))
    }

    @Test
    fun repeatedForeground_keepsTheFirstStart() {
        val dwell = ScreenDwellAccumulator()

        dwell.onForeground(0)
        dwell.onForeground(3_000)

        assertEquals(5_000L, dwell.finish(5_000))
    }

    @Test
    fun accumulatedMs_containsOnlyCompletedIntervals() {
        val dwell = ScreenDwellAccumulator()

        dwell.onForeground(0)
        dwell.onBackground(2_500)
        dwell.onForeground(4_000)

        assertEquals(2_500L, dwell.accumulatedMs)
    }

    @Test
    fun restoredTime_isAddedToTheNewInterval() {
        val dwell = ScreenDwellAccumulator(initialMs = 4_000)

        dwell.onForeground(10_000)

        assertEquals(6_000L, dwell.finish(12_000))
    }

    @Test
    fun finish_returnsNullOutsideTheValidRange() {
        val tooShort = ScreenDwellAccumulator().apply { onForeground(0) }
        val tooLong = ScreenDwellAccumulator().apply { onForeground(0) }

        assertNull(tooShort.finish(500))
        assertNull(tooLong.finish(31 * 60_000L))
    }

    @Test
    fun finish_resetsTheAccumulatedTime() {
        val dwell = ScreenDwellAccumulator()

        dwell.onForeground(0)
        dwell.finish(5_000)

        assertEquals(0L, dwell.accumulatedMs)
    }
}
