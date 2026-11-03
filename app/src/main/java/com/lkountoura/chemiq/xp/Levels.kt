package com.lkountoura.chemiq.xp

object Levels {
    // Τα XP όπου ξεκινάει ένα επίπεδο.
    fun startOf(level: Int): Int = 25 * level * (level - 1)

    fun levelFor(xp: Int): Int {
        var level = 1
        while (xp >= startOf(level + 1)) level++
        return level
    }

    // Πόσο έχει γεμίσει η μπάρα μέσα στο τρέχον επίπεδο (0f..1f).
    fun progressInLevel(xp: Int): Float {
        val level = levelFor(xp)
        val start = startOf(level)
        val end = startOf(level + 1)
        return (xp - start).toFloat() / (end - start)
    }
}
