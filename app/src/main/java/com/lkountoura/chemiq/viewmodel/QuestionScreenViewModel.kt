package com.lkountoura.chemiq.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.lkountoura.chemiq.data.dto.QuestionDTO
import com.lkountoura.chemiq.navigation.routes.Question
import com.lkountoura.chemiq.provider.QuestionScreenProvider
import com.lkountoura.chemiq.ui.state.QuestionScreenState
import com.lkountoura.chemiq.usecase.questionsUseCase.FetchQuestionsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.ObserveQuestionsUseCase
import com.lkountoura.chemiq.usecase.questionsUseCase.StoreWrongAnswerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

data class QuizAttempt(
    val questions: List<QuestionDTO>,
    val currentIndex: Int = 0,
    val selectedOptions: List<Int?> = List(questions.size) { null },
) {
    val currentQuestion: QuestionDTO
        get() = questions[currentIndex]

    val currentSelection: Int?
        get() = selectedOptions[currentIndex]

    val isLast: Boolean
        get() = currentIndex == questions.lastIndex

    fun isCorrect(index: Int): Boolean? =
        selectedOptions[index]?.let { option -> option == questions[index].correct }

    fun answer(option: Int): QuizAttempt {
        if (currentSelection != null) return this
        return copy(
            selectedOptions = selectedOptions.toMutableList().also { it[currentIndex] = option }
        )
    }

    fun next(): QuizAttempt {
        if (currentSelection == null || isLast) return this
        return copy(currentIndex = currentIndex + 1)
    }

    companion object {
        fun draw(
            pool: List<QuestionDTO>,
            size: Int,
            random: Random = Random.Default,
        ): QuizAttempt = QuizAttempt(
            questions = pool.distinctBy { it.id }.shuffled(random).take(size)
        )

        fun restore(
            pool: List<QuestionDTO>,
            questionIds: IntArray?,
            currentIndex: Int?,
            selectedOptions: IntArray?,
        ): QuizAttempt? {
            if (questionIds == null || currentIndex == null || selectedOptions == null) return null
            if (questionIds.isEmpty() || selectedOptions.size != questionIds.size) return null
            if (currentIndex !in questionIds.indices) return null

            val poolById = pool.associateBy { it.id }
            val questions = questionIds.map { id -> poolById[id] ?: return null }

            return QuizAttempt(
                questions = questions,
                currentIndex = currentIndex,
                selectedOptions = selectedOptions.map { option -> option.takeIf { it != NO_OPTION } }
            )
        }

        const val NO_OPTION = 0
    }
}

@HiltViewModel
class QuestionScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val provider: QuestionScreenProvider,
    val fetchQuestionsUseCase: FetchQuestionsUseCase,
    val observeQuestionsUseCase: ObserveQuestionsUseCase,
    val storeWrongAnswerUseCase: StoreWrongAnswerUseCase,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<Question>()
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
        // One snapshot per attempt: later Room emissions must not reshuffle or replace the questions.
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
    }
}
