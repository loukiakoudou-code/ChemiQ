package com.lkountoura.chemiq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkountoura.chemiq.provider.QuizSelectionScreenProvider
import com.lkountoura.chemiq.ui.state.QuizUnitSelectionScreenState
import com.lkountoura.chemiq.usecase.FetchUnitsUseCase
import com.lkountoura.chemiq.usecase.ObserveUnitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface QuizSelectionScreenViewModelActions {
    fun onUnitTabPressed(tag: String)
}

@HiltViewModel
class QuizSelectionScreenViewModel @Inject constructor(
    val provider: QuizSelectionScreenProvider,
    val fetchUnitsUseCase: FetchUnitsUseCase,
    val observeUnitsUseCase: ObserveUnitsUseCase,
) : ViewModel(), QuizSelectionScreenViewModelActions {

    private var _uiState: MutableStateFlow<QuizUnitSelectionScreenState> =
        MutableStateFlow<QuizUnitSelectionScreenState>(provider.provideLoading())
    val uiState: MutableStateFlow<QuizUnitSelectionScreenState>
        get() = _uiState

    init {
        fetchUnits()
    }

    fun fetchUnits() {
        viewModelScope.launch {
            fetchUnitsUseCase.execute()
            observeUnits()
        }
    }

    fun observeUnits() {
        viewModelScope.launch {
            observeUnitsUseCase.execute().collect { units ->
                _uiState.update {
                    provider.provideData(
                        newData = units
                    )
                }
            }
        }
    }

    fun onDifficultyBoxPressed(tag: String) {
        if (_uiState.value is QuizUnitSelectionScreenState.Loading) return

        uiState.update { oldState ->
            provider.provideDifficultyLevel(
                tag = tag,
                oldState = oldState
            )
        }
    }

    override fun onUnitTabPressed(tag: String) {
        uiState.update { oldState ->
            provider.provideSelectedUnit(
                oldState = oldState as QuizUnitSelectionScreenState.Data,
                tag = tag
            )
        }
    }
}