package com.lkountoura.chemiq.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lkountoura.chemiq.navigation.routes.TimeAttack
import com.lkountoura.chemiq.provider.TimeAttackScreenProvider
import com.lkountoura.chemiq.ui.state.TimeAttackScreenState
import com.lkountoura.chemiq.usecase.questionsUseCase.FetchQuestionsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.ObserveAllQuestionsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreResultsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreXpUseCase
import com.lkountoura.chemiq.viewmodel.helpers.TimeAttackRun
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface TimeAttackNavigation {
    data object Back : TimeAttackNavigation
    data object End : TimeAttackNavigation
}

@HiltViewModel
class TimeAttackScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val provider: TimeAttackScreenProvider,
    val fetchQuestionsUseCase: FetchQuestionsUseCase,
    val observeAllQuestionsUseCase: ObserveAllQuestionsUseCase,
    val storeXpUseCase: StoreXpUseCase,
    val storeResultsUseCase: StoreResultsUseCase,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<TimeAttack>()

    private val _navigation = Channel<TimeAttackNavigation>()
    val navigation = _navigation.receiveAsFlow()
    private var _uiState: MutableStateFlow<TimeAttackScreenState> =
        MutableStateFlow<TimeAttackScreenState>(provider.provideEmpty())
    val uiState: MutableStateFlow<TimeAttackScreenState>
        get() = _uiState

    private var run: TimeAttackRun? = null
    private var isFinished = false


    init {
        fetchQuestions()
    }

    fun fetchQuestions() {
        viewModelScope.launch {
            fetchQuestionsUseCase.execute()
            val pool = observeAllQuestionsUseCase.execute().first()
            if (pool.isEmpty()) return@launch

            updateRun(TimeAttackRun.start(pool))
            runTimer()
        }
    }

    private suspend fun runTimer() {
        while (true) {
            delay(1_000)
            val current = run ?: return
            val ticked = current.tick()
            updateRun(ticked)

            if (ticked.isOver) {
                finishGame(ticked)
                return
            }
        }
    }

    fun onAnswerPressed(tag: String) {
        val current = run ?: return
        val option = tag.toIntOrNull() ?: return

        val answered = current.answer(option)
        if (answered == current) return

        updateRun(answered)


        viewModelScope.launch {
            delay(FEEDBACK_MS)
            val latest = run ?: return@launch
            if (latest.isOver) return@launch
            updateRun(latest.next())
        }
    }

    fun onClosePressed() {
        viewModelScope.launch {
            _navigation.send(TimeAttackNavigation.Back)
        }
    }

    private fun finishGame(finished: TimeAttackRun) {
        if (isFinished) return
        isFinished = true

        viewModelScope.launch {
            storeXpUseCase.execute(xpEarned = finished.xpEarned)
            storeResultsUseCase.execute(
                time = TimeAttackRun.TOTAL_SECONDS * 1_000L,
                seri = finished.bestStreak,
                correctAnswers = finished.correctCount,
                answeredCount = finished.answeredCount,
                game = "timeAttack",
                unitTitle = null,
                difficulty = null
            )
            _navigation.send(TimeAttackNavigation.End)
        }
    }

    private fun updateRun(newRun: TimeAttackRun) {
        run = newRun
        _uiState.update { provider.provideData(run = newRun) }
    }

    private companion object {
        const val FEEDBACK_MS = 1_000L
    }
}