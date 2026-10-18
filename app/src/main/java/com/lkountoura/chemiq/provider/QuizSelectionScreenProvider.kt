package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.ui.component.common.ButtonLayout
import com.lkountoura.chemiq.ui.component.common.ButtonState
import com.lkountoura.chemiq.ui.component.common.ChipColor
import com.lkountoura.chemiq.ui.component.common.ChipIcon
import com.lkountoura.chemiq.ui.component.common.NavigationIcon
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.component.unitSelectionPage.DifficultyBoxState
import com.lkountoura.chemiq.ui.component.unitSelectionPage.DifficultyPanelState
import com.lkountoura.chemiq.ui.component.unitSelectionPage.UnitPanelState
import com.lkountoura.chemiq.ui.component.unitSelectionPage.UnitTabState
import com.lkountoura.chemiq.ui.state.QuizUnitSelectionScreenState
import javax.inject.Inject

class QuizSelectionScreenProvider @Inject constructor() {
    fun provideData(): QuizUnitSelectionScreenState.Data {
        val unit1 = UnitTabState(tag = "quiz1").setUnitTabState(
            index = 1,
            title = "Δομή ατόμου & Περιοδικός Πίνακας",
            progress = 0.5f,
            isSelected = true
        )
        val unit2 = UnitTabState(tag = "quiz2").setUnitTabState(
            index = 2,
            title = "Χημικοί δεσμοί",
            progress = 0.7f,
            isSelected = false
        )
        val unit3 = UnitTabState(tag = "quiz3").setUnitTabState(
            index = 3,
            title = "Ονοματολογία ανόργανων",
            progress = 0.9f,
            isSelected = false
        )
        val unit4 = UnitTabState(tag = "quiz4").setUnitTabState(
            index = 4,
            title = "Χημικές αντιδράσεις",
            progress = 0.6f,
            isSelected = false
        )
        val unit5 = UnitTabState(tag = "quiz5").setUnitTabState(
            index = 5,
            title = "Mol & στοιχειομετρία",
            progress = 0.2f,
            isSelected = false
        )
        val easy = DifficultyBoxState(tag = "easy").setDifficultyBoxState(
            text = "Εύκολο",
            isSelected = false
        )
        val medium = DifficultyBoxState(tag = "medium").setDifficultyBoxState(
            text = "Μέτριο",
            isSelected = true
        )
        val hard = DifficultyBoxState(tag = "hard").setDifficultyBoxState(
            text = "Δύσκολο",
            isSelected = false
        )

        return QuizUnitSelectionScreenState.Data(
            topBarNavigationState = TopBarNavigationState(
                navigationIcon = NavigationIcon.BACK,
                title = "Quiz",
                chipText = "10 ερωτήσεις",
                chipIcon = ChipIcon.FLASK,
                chipColor = ChipColor.PURPLE
            ),
            unitPanelState = UnitPanelState(
                text = "Διάλεξε ενότητα",
                helpText = "Το ποσοστό δείχνει την επιτυχία σου σε κάθε ενότητα",
                unitTabs = listOf(unit1, unit2, unit3, unit4, unit5)
            ),
            difficultyPanelState = DifficultyPanelState(
                difficultyBoxes = listOf(easy, medium, hard)
            ),
            buttonState = ButtonState(
                tag = "",
                _mainButtonText = "Ξεκίνα",
                _buttonLayout = ButtonLayout.ONE_BUTTON
            )
        )
    }

    fun provideDifficultyLevel(
        tag: String,
        oldState: QuizUnitSelectionScreenState
    ): QuizUnitSelectionScreenState.Data {

        val difficultyBoxes = when (tag) {
            "easy" -> {
                oldState.difficultyPanelState.difficultyBoxes.map { box ->
                    box.copy(isSelected = box.tag == tag)
                }
            }

            "medium" -> {
                oldState.difficultyPanelState.difficultyBoxes.map { box ->
                    box.copy(isSelected = box.tag == tag)
                }

            }

            "hard" -> {
                oldState.difficultyPanelState.difficultyBoxes.map { box ->
                    box.copy(isSelected = box.tag == tag)
                }
            }

            else -> null
        }

        return QuizUnitSelectionScreenState.Data(
            buttonState = oldState.buttonState,
            topBarNavigationState = oldState.topBarNavigationState,
            unitPanelState = oldState.unitPanelState,
            difficultyPanelState = oldState.difficultyPanelState.copy(
                difficultyBoxes = difficultyBoxes ?: emptyList()
            )
        )
    }
}