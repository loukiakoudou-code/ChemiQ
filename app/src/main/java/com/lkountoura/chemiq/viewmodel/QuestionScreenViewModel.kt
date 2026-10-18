package com.lkountoura.chemiq.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.lkountoura.chemiq.navigation.routes.Question
import com.lkountoura.chemiq.provider.QuestionScreenProvider
import com.lkountoura.chemiq.ui.state.QuestionScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class QuestionScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val provider: QuestionScreenProvider
) : ViewModel() {
    private val route = savedStateHandle.toRoute<Question>()
    private var _uiState: MutableStateFlow<QuestionScreenState> =
        MutableStateFlow<QuestionScreenState>(provider.provideData())
    val uiState: MutableStateFlow<QuestionScreenState>
        get() = _uiState
}