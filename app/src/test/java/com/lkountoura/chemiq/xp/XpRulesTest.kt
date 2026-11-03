package com.lkountoura.chemiq.xp

import org.junit.Assert.assertEquals
import org.junit.Test

class XpRulesTest {

    @Test
    fun quizTotal_isFivePerCorrectTimesDifficulty() {
        assertEquals(80, XpRules.quizTotal(correct = 8, total = 10, difficulty = 2))
    }

    @Test
    fun quizTotal_addsTheBonusWhenAllAreCorrect() {
        assertEquals(120, XpRules.quizTotal(correct = 10, total = 10, difficulty = 2))
    }

    @Test
    fun quizTotal_isZeroWithoutCorrectAnswers() {
        assertEquals(0, XpRules.quizTotal(correct = 0, total = 10, difficulty = 3))
    }

    @Test
    fun timeAttackCorrect_givesFiveBeforeTheStreak() {
        assertEquals(5, XpRules.timeAttackCorrect(streak = 2))
    }

    @Test
    fun timeAttackCorrect_doublesFromTheThirdInARow() {
        assertEquals(10, XpRules.timeAttackCorrect(streak = 3))
    }

    @Test
    fun periodicTotal_neverGoesBelowZero() {
        assertEquals(0, XpRules.periodicTotal(correct = 1, hintsUsed = 3))
    }

    @Test
    fun matchingTotal_addsTheBonusWithoutMistakes() {
        assertEquals(40, XpRules.matchingTotal(pairs = 5, mistakes = 0))
    }

    @Test
    fun matchingTotal_hasNoBonusWithMistakes() {
        assertEquals(25, XpRules.matchingTotal(pairs = 5, mistakes = 2))
    }
}
