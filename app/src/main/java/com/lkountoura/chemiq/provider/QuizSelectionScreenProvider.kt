package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.data.dto.QuizSelectionUnitDTO
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
    fun provideLoading(): QuizUnitSelectionScreenState.Loading {
        return QuizUnitSelectionScreenState.Loading(
            topBarNavigationState = provideTopBarNavigationState(),
            unitPanelState = UnitPanelState(),
            difficultyPanelState = DifficultyPanelState(),
            buttonState = provideButtonState()
        )
    }

    fun provideData(
        newData: List<QuizSelectionUnitDTO>
    ): QuizUnitSelectionScreenState.Data {

        val unitListsState = newData.map { unit ->
            UnitTabState(
                tag = unit.id.toString(),
            ).setUnitTabState(
                title = unit.title,
                index = unit.sortOrder,
                progress = 0.5f,
                isSelected = false
            )
        }

        val easy = DifficultyBoxState(tag = "1").setDifficultyBoxState(
            text = "Εύκολο",
            isSelected = false
        )
        val medium = DifficultyBoxState(tag = "2").setDifficultyBoxState(
            text = "Μέτριο",
            isSelected = true
        )
        val hard = DifficultyBoxState(tag = "3").setDifficultyBoxState(
            text = "Δύσκολο",
            isSelected = false
        )

        return QuizUnitSelectionScreenState.Data(
            topBarNavigationState = provideTopBarNavigationState(),
            unitPanelState = UnitPanelState(
                text = "Διάλεξε ενότητα",
                helpText = "Το ποσοστό δείχνει την επιτυχία σου σε κάθε ενότητα",
                unitTabs = unitListsState
            ),
            difficultyPanelState = DifficultyPanelState(
                difficultyBoxes = listOf(easy, medium, hard)
            ),
            buttonState = provideButtonState()
        )
    }

    private fun provideTopBarNavigationState(): TopBarNavigationState = TopBarNavigationState(
        navigationIcon = NavigationIcon.BACK,
        title = "Quiz",
        chipText = "10 ερωτήσεις",
        chipIcon = ChipIcon.FLASK,
        chipColor = ChipColor.PURPLE
    )

    private fun provideButtonState(): ButtonState = ButtonState(
        tag = "",
        _mainButtonText = "Ξεκίνα",
        _buttonLayout = ButtonLayout.ONE_BUTTON
    )

    fun provideDifficultyLevel(
        tag: String,
        oldState: QuizUnitSelectionScreenState
    ): QuizUnitSelectionScreenState.Data {

        val difficultyBoxes = oldState.difficultyPanelState.difficultyBoxes.map { box ->
            box.copy(isSelected = box.tag == tag)
        }

        return QuizUnitSelectionScreenState.Data(
            buttonState = oldState.buttonState,
            topBarNavigationState = oldState.topBarNavigationState,
            unitPanelState = oldState.unitPanelState,
            difficultyPanelState = oldState.difficultyPanelState.copy(
                difficultyBoxes = difficultyBoxes
            )
        )
    }

    fun provideSelectedUnit(
        oldState: QuizUnitSelectionScreenState.Data,
        tag: String
    ): QuizUnitSelectionScreenState.Data {
        val unitTabs = oldState.unitPanelState.unitTabs.map { unit ->
            unit.setUnitTabState(
                title = unit.title,
                index = unit.index,
                progress = unit.progress,
                isSelected = unit.tag == tag
            )
        }
        return oldState.copy(
            unitPanelState = oldState.unitPanelState.setUnitPanelState(
                unitTabs = unitTabs
            )
        )
    }
}