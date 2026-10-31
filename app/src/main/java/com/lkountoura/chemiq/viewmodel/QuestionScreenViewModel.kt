package com.lkountoura.chemiq.viewmodel

import android.os.SystemClock
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lkountoura.chemiq.navigation.routes.Question
import com.lkountoura.chemiq.provider.QuestionScreenProvider
import com.lkountoura.chemiq.ui.state.QuestionScreenState
import com.lkountoura.chemiq.usecase.questionsUseCase.FetchQuestionsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.ObserveQuestionsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreResultsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreWrongAnswerUseCase
import com.lkountoura.chemiq.viewmodel.helpers.QuizAttempt
import com.lkountoura.chemiq.viewmodel.helpers.ScreenDwellAccumulator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuestionScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val provider: QuestionScreenProvider,
    val fetchQuestionsUseCase: FetchQuestionsUseCase,
    val observeQuestionsUseCase: ObserveQuestionsUseCase,
    val storeWrongAnswerUseCase: StoreWrongAnswerUseCase,
    val storeResultsUseCase: StoreResultsUseCase,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<Question>()
    private val dwell = ScreenDwellAccumulator(
        initialMs = savedStateHandle.get<Long>(KEY_DWELL_MS) ?: 0L
    )

    private var _uiState: MutableStateFlow<QuestionScreenState> =
        MutableStateFlow<QuestionScreenState>(provider.provideLoading())
    val uiState: MutableStateFlow<QuestionScreenState>
        get() = _uiState

    private var attempt: QuizAttempt? = null

    init {
        fetchQuestions()
    }

    fun fetchQuestions() {
        viewModelScope.launch {
            fetchQuestionsUseCase.execute()
            loadAttempt()
        }
    }

    private suspend fun loadAttempt() {
        val pool = observeQuestionsUseCase.execute(
            unitId = route.topicId,
            difficulty = route.difficulty.toIntOrNull() ?: 2
        ).first()

        val loadedAttempt = QuizAttempt.restore(
            pool = pool,
            questionIds = savedStateHandle[KEY_QUESTION_IDS],
            currentIndex = savedStateHandle[KEY_CURRENT_INDEX],
            selectedOptions = savedStateHandle[KEY_SELECTED_OPTIONS]
        ) ?: QuizAttempt.draw(pool = pool, size = QUIZ_SIZE)

        if (loadedAttempt.questions.isEmpty()) {
            _uiState.update { provider.provideEmpty() }
            return
        }

        updateAttempt(loadedAttempt)
    }

    fun onScreenStarted() {
        if (attempt?.isFinished == true) return
        dwell.onForeground(SystemClock.elapsedRealtime())
    }

    fun onScreenStopped() {
        dwell.onBackground(SystemClock.elapsedRealtime())
        savedStateHandle[KEY_DWELL_MS] = dwell.accumulatedMs
    }

    fun onAnswerPressed(tag: String) {
        val currentAttempt = attempt ?: return
        val option = tag.toIntOrNull() ?: return

        val answeredAttempt = currentAttempt.answer(option)
        if (answeredAttempt == currentAttempt) return

        updateAttempt(answeredAttempt)

        if (answeredAttempt.isCorrect(answeredAttempt.currentIndex) == false) {
            viewModelScope.launch {
                storeWrongAnswerUseCase.execute(questionId = answeredAttempt.currentQuestion.id)
            }
        }

        if (answeredAttempt.isFinished) {
            finishAttempt(answeredAttempt)
        }
    }

    private fun finishAttempt(finishedAttempt: QuizAttempt) {
        val timeMs = dwell.finish(SystemClock.elapsedRealtime())

        viewModelScope.launch {
            storeResultsUseCase.execute(
                time = timeMs,
                seri = finishedAttempt.bestStreak,
                correctAnswers = finishedAttempt.correctCount
            )
        }
    }

    fun onNextPressed() {
        val currentAttempt = attempt ?: return

        val nextAttempt = currentAttempt.next()
        if (nextAttempt == currentAttempt) return

        updateAttempt(nextAttempt)
    }

    private fun updateAttempt(newAttempt: QuizAttempt) {
        attempt = newAttempt

        savedStateHandle[KEY_QUESTION_IDS] = newAttempt.questions.map { it.id }.toIntArray()
        savedStateHandle[KEY_CURRENT_INDEX] = newAttempt.currentIndex
        savedStateHandle[KEY_SELECTED_OPTIONS] =
            newAttempt.selectedOptions.map { it ?: QuizAttempt.NO_OPTION }.toIntArray()

        _uiState.update {
            provider.provideData(attempt = newAttempt)
        }
    }

    private companion object {
        const val QUIZ_SIZE = 10
        const val KEY_QUESTION_IDS = "quiz_question_ids"
        const val KEY_CURRENT_INDEX = "quiz_current_index"
        const val KEY_SELECTED_OPTIONS = "quiz_selected_options"
        const val KEY_DWELL_MS = "quiz_dwell_ms"
    }
}
