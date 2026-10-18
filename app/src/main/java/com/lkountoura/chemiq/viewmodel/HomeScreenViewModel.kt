package com.lkountoura.chemiq.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkountoura.chemiq.provider.HomeScreenProvider
import com.lkountoura.chemiq.ui.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeScreenViewModelActions {
    fun onGameTabPressed(tag: String)
}

sealed interface HomeNavigation {
    data object ToTopicSelect : HomeNavigation
    data object ToPeriodicTable : HomeNavigation
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    provider: HomeScreenProvider
) : ViewModel(), HomeScreenViewModelActions {

    private val _navigation = Channel<HomeNavigation>()
    val navigation = _navigation.receiveAsFlow()

    private var _uiState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow<HomeScreenState>(provider.provideData())
    val uiState: MutableStateFlow<HomeScreenState>
        get() = _uiState

    override fun onGameTabPressed(tag: String) {
        viewModelScope.launch {
            when (tag) {
                "quiz" -> _navigation.send(HomeNavigation.ToTopicSelect)
                "periodic" -> _navigation.send(HomeNavigation.ToPeriodicTable)
            }
        }
    }
}