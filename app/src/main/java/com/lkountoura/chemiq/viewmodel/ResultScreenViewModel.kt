package com.lkountoura.chemiq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkountoura.chemiq.usecase.resultUseCase.ObserveResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultScreenViewModel @Inject constructor(
    val observeResultUseCase: ObserveResultUseCase
) : ViewModel() {

    init {
        observeResults()
    }

    private fun observeResults() {
        viewModelScope.launch {
            observeResultUseCase.execute().collect { result ->

            }
        }
    }
}
