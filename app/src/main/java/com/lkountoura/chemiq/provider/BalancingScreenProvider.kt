package com.lkountoura.chemiq.provider

import com.lkountoura.chemiq.data.dto.EquationDTO
import com.lkountoura.chemiq.ui.component.balancePage.AtomRowState
import com.lkountoura.chemiq.ui.component.balancePage.AtomRowStatus
import com.lkountoura.chemiq.ui.component.balancePage.AtomsTableState
import com.lkountoura.chemiq.ui.component.balancePage.BalanceDifficulty
import com.lkountoura.chemiq.ui.component.balancePage.BalanceLabelRowState
import com.lkountoura.chemiq.ui.component.balancePage.BalanceMessageState
import com.lkountoura.chemiq.ui.component.balancePage.CoefficientStepperState
import com.lkountoura.chemiq.ui.component.balancePage.CoefficientStepperStatus
import com.lkountoura.chemiq.ui.component.balancePage.EquationCardState
import com.lkountoura.chemiq.ui.component.balancePage.SpeciesItemState
import com.lkountoura.chemiq.ui.component.common.ButtonLayout
import com.lkountoura.chemiq.ui.component.common.ButtonState
import com.lkountoura.chemiq.ui.component.common.ChipColor
import com.lkountoura.chemiq.ui.component.common.NavigationIcon
import com.lkountoura.chemiq.ui.component.common.TopBarNavigationState
import com.lkountoura.chemiq.ui.state.BalancingScreenState
import com.lkountoura.chemiq.usecase.equationUseCase.AtomCountDTO
import javax.inject.Inject

class BalancingScreenProvider @Inject constructor() {

    fun provideEmpty(): BalancingScreenState = BalancingScreenState.Empty

    fun provideData(
        equation: EquationDTO,
        coefficients: List<Int>,
        selectedIndex: Int?,
        atoms: List<AtomCountDTO>?,
        isChecked: Boolean,
        number: Int,
        total: Int,
    ): BalancingScreenState {


        val reactants = equation.reactants.mapIndexed { i, species ->
            SpeciesItemState(
                formula = toSubscript(species.formula),
                stepperState = provideStepper(i, coefficients, selectedIndex)
            )
        }
        val products = equation.products.mapIndexed { i, species ->
            SpeciesItemState(
                formula = toSubscript(species.formula),
                stepperState = provideStepper(
                    equation.reactants.size + i,
                    coefficients,
                    selectedIndex
                )
            )
        }

        val atomsTableState = atoms?.let { list ->
            AtomsTableState(
                rows = list.map { atom ->
                    AtomRowState(
                        atom = atom.atom,
                        reactants = atom.reactants,
                        products = atom.products,
                        status = if (atom.isBalanced) AtomRowStatus.BALANCED else AtomRowStatus.UNBALANCED
                    )
                }
            )
        }
        
        var difficulty = BalanceDifficulty.EASY
        var difficultyText = "Εύκολο"

        if (equation.difficulty == 2) {
            difficulty = BalanceDifficulty.MEDIUM
            difficultyText = "Μέτριο"
        }

        if (equation.difficulty == 3) {
            difficulty = BalanceDifficulty.HARD
            difficultyText = "Δύσκολο"
        }

        var balanceMessageState: BalanceMessageState? = null

        if (isChecked && atoms != null) {
            for (atom in atoms) {
                if (atom.reactants != atom.products) {
                    balanceMessageState = BalanceMessageState(
                        text = "Το ${atom.atom} δεν ισοσταθμίζεται ακόμα."
                    )
                    break
                }
            }
        }

        return BalancingScreenState.Data(
            topBarNavigationState = TopBarNavigationState(
                navigationIcon = NavigationIcon.BACK,
                title = "Ισοστάθμιση",
                chipText = "$number / $total",
                chipColor = ChipColor.BLUE
            ),
            labelRowState = BalanceLabelRowState(
                difficultyText = difficultyText,
                difficulty = difficulty
            ),
            equationCardState = EquationCardState(
                reactants = reactants,
                products = products
            ),
            atomsTableState = atomsTableState,
            balanceMessageState = balanceMessageState,
            buttonState = ButtonState(
                tag = "balance",
                _mainButtonText = "Έλεγχος",
                _secondaryButtonText = "Υπόδειξη",
                _buttonLayout = ButtonLayout.TWO_BUTTON_ROW
            )
        )
    }


    private fun provideStepper(
        index: Int,
        coefficients: List<Int>,
        selectedIndex: Int?,
    ): CoefficientStepperState {
        return CoefficientStepperState(tag = index.toString()).setCoefficientStepperState(
            value = coefficients[index],
            status = if (selectedIndex == index) CoefficientStepperStatus.SELECTED else CoefficientStepperStatus.DEFAULT
        )
    }

    private fun toSubscript(formula: String): String {
        val subscripts = "₀₁₂₃₄₅₆₇₈₉"
        return formula.map { char ->
            if (char.isDigit()) subscripts[char.digitToInt()] else char
        }.joinToString("")
    }
}
