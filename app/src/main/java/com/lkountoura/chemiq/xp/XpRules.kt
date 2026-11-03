package com.lkountoura.chemiq.xp

object XpRules {
    const val QUIZ_PER_DIFFICULTY = 5
    const val QUIZ_PERFECT_BONUS = 20

    const val TIME_ATTACK_CORRECT = 5
    const val TIME_ATTACK_STREAK_FOR_DOUBLE = 3

    const val PERIODIC_CORRECT = 10
    const val PERIODIC_HINT_COST = 5

    const val EQUATION_PER_DIFFICULTY = 10

    const val MATCH_PAIR = 5
    const val MATCH_NO_MISTAKES_BONUS = 15

    const val REVIEW_CORRECT = 5

    fun quizTotal(correct: Int, total: Int, difficulty: Int): Int {
        val bonus = if (total > 0 && correct == total) QUIZ_PERFECT_BONUS else 0
        return correct * QUIZ_PER_DIFFICULTY * difficulty + bonus
    }

    // XP για μία σωστή στο Time Attack. Το streak μετράει και την τρέχουσα σωστή.
    fun timeAttackCorrect(streak: Int): Int =
        if (streak >= TIME_ATTACK_STREAK_FOR_DOUBLE) TIME_ATTACK_CORRECT * 2 else TIME_ATTACK_CORRECT

    fun periodicTotal(correct: Int, hintsUsed: Int): Int =
        (correct * PERIODIC_CORRECT - hintsUsed * PERIODIC_HINT_COST).coerceAtLeast(0)

    fun balancingTotal(solvedDifficulties: List<Int>): Int =
        solvedDifficulties.sumOf { it * EQUATION_PER_DIFFICULTY }

    fun matchingTotal(pairs: Int, mistakes: Int): Int =
        pairs * MATCH_PAIR + if (mistakes == 0) MATCH_NO_MISTAKES_BONUS else 0

    fun reviewTotal(correct: Int): Int = correct * REVIEW_CORRECT
}
