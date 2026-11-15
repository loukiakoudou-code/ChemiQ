package com.lkountoura.chemiq.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkountoura.chemiq.provider.HomeScreenProvider
import com.lkountoura.chemiq.ui.state.HomeScreenState
import com.lkountoura.chemiq.usecase.questionsUseCase.ObserveReviewQuestionsCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeScreenViewModelActions {
    fun onGameTabPressed(tag: String)
    fun onBottomSheetDismissed()
    fun onDifficultyPressed(tag: String)
    fun onStartPressed()
}

sealed interface HomeNavigation {
    data object ToTopicSelect : HomeNavigation
    data object ToPeriodicTable : HomeNavigation

    data object TimeAttack : HomeNavigation
    data object Matching : HomeNavigation
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val provider: HomeScreenProvider,
    val observeReviewQuestionsCountUseCase: ObserveReviewQuestionsCountUseCase,
) : ViewModel(), HomeScreenViewModelActions {

    private val _navigation = Channel<HomeNavigation>()
    val navigation = _navigation.receiveAsFlow()

    private var _uiState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow<HomeScreenState>(provider.provideData())
    val uiState: MutableStateFlow<HomeScreenState>
        get() = _uiState

    init {
        observeReviewQuestionsCount()
    }

    private fun observeReviewQuestionsCount() {
        viewModelScope.launch {
            observeReviewQuestionsCountUseCase.execute().collect { count ->
                _uiState.update { oldState ->
                    provider.provideData(reviewQuestionsCount = count).copy(
                        isBottomSheetOpen = oldState.isBottomSheetOpen,
                        difficultySheetState = oldState.difficultySheetState
                    )
                }

            }
        }
    }

    override fun onGameTabPressed(tag: String) {
        viewModelScope.launch {
            when (tag) {
                "quiz" -> _navigation.send(HomeNavigation.ToTopicSelect)
                "periodic" -> _navigation.send(HomeNavigation.ToPeriodicTable)
                "time_attack" -> _navigation.send(HomeNavigation.TimeAttack)
                "match" -> _navigation.send(HomeNavigation.Matching)
                "balance" -> _uiState.update { oldState ->
                    provider.provideToggleBottomSheet(oldState = oldState)
                }
            }
        }
    }

    override fun onBottomSheetDismissed() {
        _uiState.update { oldState ->
            provider.provideToggleBottomSheet(oldState = oldState)
        }
    }

    override fun onDifficultyPressed(tag: String) {
        _uiState.update { oldState ->
            provider.provideSelectedDifficulty(tag = tag, oldState = oldState)
        }
    }

    override fun onStartPressed() {
        _uiState.update { oldState ->
            provider.provideToggleBottomSheet(oldState = oldState)
        }
        // Αργότερα: navigation στο παιχνίδι Ισοστάθμιση με τη δυσκολία
    }
}