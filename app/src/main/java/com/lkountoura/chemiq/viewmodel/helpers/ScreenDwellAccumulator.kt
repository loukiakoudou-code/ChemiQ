package com.lkountoura.chemiq.viewmodel.helpers

internal class ScreenDwellAccumulator(initialMs: Long = 0L) {
    private var startedAt: Long? = null
    var accumulatedMs = initialMs
        private set

    fun onForeground(nowElapsedMs: Long) {
        if (startedAt == null) startedAt = nowElapsedMs
    }

    fun onBackground(nowElapsedMs: Long) {
        val started = startedAt ?: return
        accumulatedMs += (nowElapsedMs - started).coerceAtLeast(0L)
        startedAt = null
    }

    fun finish(nowElapsedMs: Long): Long? {
        onBackground(nowElapsedMs)
        val total = accumulatedMs
        accumulatedMs = 0L
        return total.takeIf { it in MIN_MS..MAX_MS }
    }

    private companion object {
        const val MIN_MS = 1_000L
        const val MAX_MS = 30 * 60_000L
    }
}