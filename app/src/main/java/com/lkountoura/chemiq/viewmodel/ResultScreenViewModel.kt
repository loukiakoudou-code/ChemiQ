package com.lkountoura.chemiq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkountoura.chemiq.provider.ResultScreenProvider
import com.lkountoura.chemiq.ui.state.ResultScreenState
import com.lkountoura.chemiq.usecase.resultUseCase.GetXpUseCase
import com.lkountoura.chemiq.usecase.resultUseCase.ObserveResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ResultNavigation {
    data object Home : ResultNavigation
    data class PlayAgain(val game: String) : ResultNavigation
    data object Review : ResultNavigation
}

@HiltViewModel
class ResultScreenViewModel @Inject constructor(
    val provider: ResultScreenProvider,
    val observeResultUseCase: ObserveResultUseCase,
    val getXpUseCase: GetXpUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow<ResultScreenState>(provider.provideEmpty())

    val uiState: MutableStateFlow<ResultScreenState>
        get() = _uiState
    private val _navigation = Channel<ResultNavigation>()
    val navigation = _navigation.receiveAsFlow()

    private var game: String = "quiz"

    init {
        observeResults()
    }

    private fun navigate(event: ResultNavigation) {
        viewModelScope.launch { _navigation.send(event) }
    }

    fun onHomePressed() = navigate(ResultNavigation.Home)
    fun onPlayAgainPressed() = navigate(ResultNavigation.PlayAgain(game))
    fun onReviewPressed() = navigate(ResultNavigation.Review)

    private fun observeResults() {
        viewModelScope.launch {
            observeResultUseCase.execute().collect { result ->
                game = result.game
                val xp = getXpUseCase.execute()
                _uiState.update { oldState ->
                    provider.provideData(result = result, xp = xp)
                }
            }
        }
    }
}
