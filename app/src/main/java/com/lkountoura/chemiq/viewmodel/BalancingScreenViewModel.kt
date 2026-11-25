package com.lkountoura.chemiq.viewmodel

import android.os.SystemClock
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lkountoura.chemiq.data.dto.EquationDTO
import com.lkountoura.chemiq.navigation.routes.Balancing
import com.lkountoura.chemiq.provider.BalancingScreenProvider
import com.lkountoura.chemiq.ui.state.BalancingScreenState
import com.lkountoura.chemiq.usecase.equationUseCase.AtomCountDTO
import com.lkountoura.chemiq.usecase.equationUseCase.CalculateAtomsUseCase
import com.lkountoura.chemiq.usecase.equationUseCase.FetchEquationsUseCase
import com.lkountoura.chemiq.usecase.equationUseCase.ObserveEquationsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreResultsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreXpUseCase
import com.lkountoura.chemiq.xp.XpRules
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface BalancingNavigation {
    data object Back : BalancingNavigation
    data object End : BalancingNavigation
}

@HiltViewModel
class BalancingScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val provider: BalancingScreenProvider,
    val fetchEquationsUseCase: FetchEquationsUseCase,
    val observeEquationsUseCase: ObserveEquationsUseCase,
    val calculateAtomsUseCase: CalculateAtomsUseCase,
    val storeXpUseCase: StoreXpUseCase,
    val storeResultsUseCase: StoreResultsUseCase,
) : ViewModel() {

    private val _navigation = Channel<BalancingNavigation>()
    val navigation = _navigation.receiveAsFlow()

    private var _uiState: MutableStateFlow<BalancingScreenState> =
        MutableStateFlow<BalancingScreenState>(provider.provideEmpty())
    val uiState: MutableStateFlow<BalancingScreenState>
        get() = _uiState

    private val difficulty = savedStateHandle.toRoute<Balancing>().difficulty

    private val roundSize = 8
    private var equations: List<EquationDTO> = emptyList()
    private var currentIndex = 0
    private var coefficients = mutableListOf<Int>()
    private var selectedIndex: Int? = null
    private var isChecked = false
    private var checkCount = 0
    private var correctCount = 0
    private var isWaiting = false

    private val startTime = SystemClock.elapsedRealtime()

    init {
        fetchEquations()
    }

    private fun fetchEquations() {
        viewModelScope.launch {
            fetchEquationsUseCase.execute()
            val allEquations = observeEquationsUseCase.execute(difficulty).first()
            equations = allEquations.shuffled().take(roundSize)

            if (equations.isNotEmpty()) {
                startEquation()
            }

        }
    }

    private fun startEquation() {
        val equation = equations[currentIndex]

        coefficients.clear()
        for (species in equation.reactants) {
            coefficients.add(1)
        }
        for (species in equation.products) {
            coefficients.add(1)
        }

        selectedIndex = null
        isChecked = false

        updateScreen()
    }

    private fun updateScreen() {
        val equation = equations[currentIndex]

        var atoms: List<AtomCountDTO>? = calculateAtomsUseCase.execute(equation, coefficients)
        if (difficulty == 3 && !isChecked) {
            atoms = null
        }

        _uiState.value = provider.provideData(
            equation = equation,
            coefficients = coefficients,
            selectedIndex = selectedIndex,
            atoms = atoms,
            isChecked = isChecked,
            number = currentIndex + 1,
            total = equations.size
        )
    }

    fun onBackPressed() {
        viewModelScope.launch {
            _navigation.send(BalancingNavigation.Back)
        }
    }

    fun onPlusPressed(tag: String) {
        if (isWaiting) return
        val index = tag.toInt()

        if (coefficients[index] < 20) {
            coefficients[index] = coefficients[index] + 1
        }

        selectedIndex = index
        isChecked = false
        updateScreen()
    }

    fun onMinusPressed(tag: String) {
        if (isWaiting) return
        val index = tag.toInt()

        if (coefficients[index] > 1) {
            coefficients[index] = coefficients[index] - 1
        }

        selectedIndex = index
        isChecked = false
        updateScreen()
    }

    fun onCheckPressed() {
        if (isWaiting) return

        isChecked = true
        checkCount = checkCount + 1
        updateScreen()

        val equation = equations[currentIndex]
        val atoms = calculateAtomsUseCase.execute(equation, coefficients)

        var isBalanced = true
        for (atom in atoms) {
            if (atom.reactants != atom.products) {
                isBalanced = false
            }
        }


        if (isBalanced) {
            correctCount = correctCount + 1
            isWaiting = true

            viewModelScope.launch {
                delay(1000)
                isWaiting = false
                goToNextEquation()
            }
        }

    }

    fun onHintPressed() {
        if (isWaiting) return

        val equation = equations[currentIndex]


        val solution = mutableListOf<Int>()
        for (species in equation.reactants) {
            solution.add(species.solution)
        }
        for (species in equation.products) {
            solution.add(species.solution)
        }


        for (i in solution.indices) {
            if (coefficients[i] != solution[i]) {
                coefficients[i] = solution[i]
                selectedIndex = i
                isChecked = false
                checkCount = checkCount + 1
                updateScreen()
                return
            }
        }
    }

    private fun goToNextEquation() {
        currentIndex = currentIndex + 1

        if (currentIndex < equations.size) {
            startEquation()
        } else {
            finishGame()
        }
    }

    private fun finishGame() {
        val time = SystemClock.elapsedRealtime() - startTime
        val xp = correctCount * difficulty * XpRules.EQUATION_PER_DIFFICULTY

        viewModelScope.launch {
            storeXpUseCase.execute(xpEarned = xp)
            storeResultsUseCase.execute(
                time = time,
                seri = 0,
                correctAnswers = correctCount,
                game = "balancing",
                unitTitle = null,
                difficulty = difficulty,
                answeredCount = checkCount
            )
            _navigation.send(BalancingNavigation.End)
        }
    }

}