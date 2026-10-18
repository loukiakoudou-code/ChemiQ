package com.lkountoura.chemiq.viewmodel

import androidx.lifecycle.ViewModel
import com.lkountoura.chemiq.provider.QuizSelectionScreenProvider
import com.lkountoura.chemiq.ui.state.QuizUnitSelectionScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface QuizSelectionScreenViewModelActions {}

@HiltViewModel
class QuizSelectionScreenViewModel @Inject constructor(
    val provider: QuizSelectionScreenProvider
) : ViewModel() {

    private var _uiState: MutableStateFlow<QuizUnitSelectionScreenState> =
        MutableStateFlow<QuizUnitSelectionScreenState>(provider.provideData())
    val uiState: MutableStateFlow<QuizUnitSelectionScreenState>
        get() = _uiState

    fun onDifficultyBoxPressed(tag: String) {
//        val old = _uiState.value
//
//        val newState = provider.provideDifficultyLevel(
//            tag = tag,
//            quizUnitSelectionScreenState = old
//        )
//
//        uiState.update {
//            newState
//        }

        uiState.update{ oldState ->
            provider.provideDifficultyLevel(
                tag = tag,
                oldState = oldState
            )
        }
    }

}