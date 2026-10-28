package com.lkountoura.chemiq.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.lkountoura.chemiq.navigation.routes.TimeAttack
import com.lkountoura.chemiq.provider.TimeAttackScreenProvider
import com.lkountoura.chemiq.ui.state.TimeAttackScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TimeAttackScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val provider: TimeAttackScreenProvider
) : ViewModel() {
    private val route = savedStateHandle.toRoute<TimeAttack>()
    private var _uiState: MutableStateFlow<TimeAttackScreenState> =
        MutableStateFlow<TimeAttackScreenState>(provider.provideData())
    val uiState: MutableStateFlow<TimeAttackScreenState>
        get() = _uiState
}