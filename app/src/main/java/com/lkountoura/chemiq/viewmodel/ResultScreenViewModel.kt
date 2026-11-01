package com.lkountoura.chemiq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkountoura.chemiq.provider.ResultScreenProvider
import com.lkountoura.chemiq.ui.state.ResultScreenState
import com.lkountoura.chemiq.usecase.resultUseCase.ObserveResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ResultScreenViewModel @Inject constructor(
    val provider: ResultScreenProvider,
    val observeResultUseCase: ObserveResultUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow<ResultScreenState>(provider.provideEmpty())

    val uiState: MutableStateFlow<ResultScreenState>
        get() = _uiState

    init {
        observeResults()
    }

    private fun observeResults() {
        viewModelScope.launch {
            observeResultUseCase.execute().collect { result ->
                _uiState.update { oldState ->
                    provider.provideData(result = result)
                }
            }
        }
    }
}
