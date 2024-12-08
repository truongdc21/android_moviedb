package com.truongdc.movie_tmdb.ui

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.truongdc.movie_tmdb.core.data.repository.MainRepository
import com.truongdc.movie_tmdb.core.designsystem.R.string
import com.truongdc.movie_tmdb.core.state.UiStateDelegateImpl
import com.truongdc.movie_tmdb.core.viewmodel.UiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieTMDBAppViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @ApplicationContext private val context: Context,
) :
    UiStateViewModel<MovieTMDBAppViewModel.UiState, MovieTMDBAppViewModel.Event>(
        UiStateDelegateImpl(UiState())
    ) {

    data class UiState(
        val isShowSettingDialog: Boolean = false,
        val isLogin: Boolean? = null,
    )

    sealed interface Event {
        data object LogoutSuccess : Event
    }

    init {
        viewModelScope.launch {
            val isLogin = mainRepository.isLogin.first()
            updateUiState { state -> state.copy(isLogin = isLogin) }
        }
    }

    fun toggleSettingDialog(isShow: Boolean) {
        asyncUpdateUiState(viewModelScope) { state -> state.copy(isShowSettingDialog = isShow) }
    }

    fun logout() = viewModelScope.launch {
        viewModelScope.launch {
            showLoading()
            mainRepository.setIsLogin(false)
            appNavigator.displayToastMessage(context.getString(string.logout_success))
            sendEvent(Event.LogoutSuccess)
            hideLoading()
        }
    }
}