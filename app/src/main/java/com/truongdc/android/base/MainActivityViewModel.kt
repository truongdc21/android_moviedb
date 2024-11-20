package com.truongdc.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truongdc.android.base.common.enums.DarkThemeConfig
import com.truongdc.android.base.data.model.AppState
import com.truongdc.android.base.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: MainRepository
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = userDataRepository.appState.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val appState: AppState) : MainActivityUiState
}
