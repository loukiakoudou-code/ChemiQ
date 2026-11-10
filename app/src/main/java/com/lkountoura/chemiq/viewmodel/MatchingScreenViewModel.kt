package com.lkountoura.chemiq.viewmodel

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkountoura.chemiq.provider.MatchingScreenProvider
import com.lkountoura.chemiq.ui.state.MatchingScreenState
import com.lkountoura.chemiq.usecase.matchPairsUseCase.FetchMatchPairsUseCase
import com.lkountoura.chemiq.usecase.matchPairsUseCase.ObserveMatchPairsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreResultsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreXpUseCase
import com.lkountoura.chemiq.viewmodel.helpers.MatchingRound
import com.lkountoura.chemiq.viewmodel.helpers.ScreenDwellAccumulator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MatchingNavigation {
    data object Back : MatchingNavigation
    data object End : MatchingNavigation
}

@HiltViewModel
class MatchingScreenViewModel @Inject constructor(
    val provider: MatchingScreenProvider,
    val fetchMatchPairsUseCase: FetchMatchPairsUseCase,
    val observeMatchPairsUseCase: ObserveMatchPairsUseCase,
    val storeXpUseCase: StoreXpUseCase,
    val storeResultsUseCase: StoreResultsUseCase,
) : ViewModel() {
    private val _navigation = Channel<MatchingNavigation>()
    val navigation = _navigation.receiveAsFlow()

    private var _uiState: MutableStateFlow<MatchingScreenState> =
        MutableStateFlow<MatchingScreenState>(provider.provideEmpty())
    val uiState: MutableStateFlow<MatchingScreenState>
        get() = _uiState

    private var round: MatchingRound? = null

    private var isFinished = false

    private val dwell = ScreenDwellAccumulator()

    init {
        fetchMatchPairs()
    }

    private fun fetchMatchPairs() {
        viewModelScope.launch {
            fetchMatchPairsUseCase.execute()
            val pool = observeMatchPairsUseCase.execute(unitId = 3).first()
            if (pool.isEmpty()) return@launch

            updateRound(MatchingRound.start(pool))
        }
    }

    private fun updateRound(newRound: MatchingRound) {
        round = newRound
        _uiState.update {
            provider.provideData(round = newRound)
        }
    }

    fun onScreenStarted() {
        if (isFinished) return
        dwell.onForeground(SystemClock.elapsedRealtime())
    }

    fun onScreenStopped() {
        dwell.onBackground(SystemClock.elapsedRealtime())
    }

    fun onClosePressed() {
        viewModelScope.launch { _navigation.send(MatchingNavigation.Back) }
    }

    fun onNamePressed(tag: String) {
        val current = round ?: return
        val id = tag.toIntOrNull() ?: return
        val updated = current.selectName(id)
        if (updated == current) return
        updateRound(updated)
    }

    fun onFormulaPressed(tag: String) {
        val current = round ?: return
        val id = tag.toIntOrNull() ?: return
        val updated = current.selectFormula(id)
        if (updated == current) return
        updateRound(updated)

        if (updated.isShowingWrong) {
            viewModelScope.launch {
                delay(FEEDBACK_MS)
                val latest = round ?: return@launch
                updateRound(latest.clearWrong())
            }
        }

        if (updated.isFinished) {
            finishGame(updated)
        }
    }

    private fun finishGame(finished: MatchingRound) {
        if (isFinished) return
        isFinished = true

        val timeMs = dwell.finish(SystemClock.elapsedRealtime())

        viewModelScope.launch {
            storeXpUseCase.execute(xpEarned = finished.xpEarned)
            storeResultsUseCase.execute(
                time = timeMs,
                seri = 0,
                correctAnswers = finished.matchedIds.size,
                answeredCount = finished.moves,
                game = "matching",
                unitTitle = null,
                difficulty = null
            )
            delay(FEEDBACK_MS)
            _navigation.send(MatchingNavigation.End)
        }
    }

    private companion object {
        const val FEEDBACK_MS = 1_000L
    }
}
