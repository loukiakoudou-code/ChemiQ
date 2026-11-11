package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.data.dto.GameResultDTO
import com.lkountoura.chemiq.data.dto.XpDTO
import com.lkountoura.chemiq.ui.component.common.ButtonLayout
import com.lkountoura.chemiq.ui.component.common.ButtonState
import com.lkountoura.chemiq.ui.component.resultScreenPage.LevelCardState
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultStatCardState
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultStatPanelState
import com.lkountoura.chemiq.ui.component.resultScreenPage.ResultTitleState
import com.lkountoura.chemiq.ui.component.resultScreenPage.ScoreRingState
import com.lkountoura.chemiq.ui.component.resultScreenPage.StatType
import com.lkountoura.chemiq.ui.state.ResultScreenState
import com.lkountoura.chemiq.xp.Levels
import javax.inject.Inject

class ResultScreenProvider @Inject constructor() {

    fun provideEmpty(): ResultScreenState.Empty {
        return ResultScreenState.Empty()
    }

    fun provideData(result: GameResultDTO, xp: XpDTO): ResultScreenState.Data {
        val total = result.answeredCount?.takeIf { it > 0 } ?: 10
        val accuracy = result.correctAnswers * 100 / total
        val time = result.time?.let { time ->
            time / 1000
        }

        val calculateTime = time?.let { time ->
            val hours = time / 3600
            val minutes = (time % 3600) / 60
            val seconds = time % 60
            "%02d:%02d:%02d".format(hours, minutes, seconds)
        }

        val tab1 = ResultStatCardState(
            result = "+${xp.xpEarned} XP",
            title = "Κέρδος",
            statType = StatType.XP
        )
        val tab2 = ResultStatCardState(
            result = "$accuracy%",
            title = "Ακρίβεια",
            statType = StatType.ACCURACY
        )
        val tab3 = ResultStatCardState(
            result = calculateTime ?: "0",
            title = "Χρόνος",
            statType = StatType.TIME
        )
        return ResultScreenState.Data(
            resultStatPanelState = ResultStatPanelState(
                resultStatCard = listOf(tab1, tab2, tab3)
            ),
            levelCardState = provideLevelCardState(xp = xp),
            chemiQButtonState = if (result.game == "quiz") {
                ButtonState(
                    tag = "restart",
                    _mainButtonText = "Αρχική",
                    _secondaryButtonText = "Ξαναπαίξε",
                    _tertiaryButtonText = "Επανάληψη λαθών (${total - result.correctAnswers})",
                    _buttonLayout = ButtonLayout.THREE_BUTTON
                )
            } else {
                ButtonState(
                    tag = "restart",
                    _mainButtonText = "Αρχική",
                    _secondaryButtonText = "Ξαναπαίξε",
                    _buttonLayout = ButtonLayout.TWO_BUTTON
                )
            },
            scoreRingState = ScoreRingState(
                score = "${result.correctAnswers}/$total",
                description = if (result.game == "matching") "ζεύγη / κινήσεις" else "σωστές",
                progress = result.correctAnswers / total.toFloat()
            ),
            resultTitleState = ResultTitleState(
                title = when {
                    accuracy >= 80 -> "Μπράβο!"
                    accuracy >= 50 -> "Καλή προσπάθεια!"
                    else -> "Συνέχισε την εξάσκηση!"
                },
                subtitle = provideSubtitle(result)
            )
        )
    }

    private fun provideLevelCardState(xp: XpDTO): LevelCardState {
        val xpAfter = xp.totalXp
        val xpBefore = xp.totalXp - xp.xpEarned
        val levelAfter = Levels.levelFor(xpAfter)
        val leveledUp = Levels.levelFor(xpBefore) < levelAfter

        val start = Levels.startOf(levelAfter)
        val end = Levels.startOf(levelAfter + 1)
        val span = (end - start).toFloat()

        val before = if (leveledUp) 0f else (xpBefore - start) / span
        val gained = if (leveledUp) (xpAfter - start) / span else xp.xpEarned / span

        return LevelCardState(tag = "level").setLevelCardState(
            title = "Επίπεδο $levelAfter",
            xpText = "$xpAfter / $end XP",
            before = before,
            gained = gained,
            caption = if (leveledUp) {
                "Ανέβηκες επίπεδο! +${xp.xpEarned} XP"
            } else {
                "+${xp.xpEarned} XP από αυτό το παιχνίδι"
            }
        )
    }

    private fun provideSubtitle(result: GameResultDTO): String {
        val game = when (result.game) {
            "quiz" -> "Quiz"
            "timeAttack" -> "Time Attack"
            "matching" -> "Αντιστοίχιση"
            else -> result.game
        }
        val difficulty = when (result.difficulty) {
            1 -> "Εύκολο"
            2 -> "Μέτριο"
            3 -> "Δύσκολο"
            else -> null
        }
        return listOfNotNull(game, result.unitTitle, difficulty).joinToString(" · ")
    }
}