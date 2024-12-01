package com.truongdc.movie_tmdb.feature.login

import androidx.lifecycle.viewModelScope
import com.truongdc.movie_tmdb.core.data.repository.MainRepository
import com.truongdc.movie_tmdb.core.navigation.AppDestination
import com.truongdc.movie_tmdb.core.state.UiStateDelegateImpl
import com.truongdc.movie_tmdb.core.viewmodel.UiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : UiStateViewModel<LoginViewModel.UiState, LoginViewModel.Event>(
    UiStateDelegateImpl(UiState())
) {
    data class UiState(
        val email: String = "",
        val pass: String = "",
        val isTextFieldFocused: Boolean = false,
        val isInValid: Boolean = true,
    )

    sealed interface Event {
        data object LoginSuccess : Event

        data object LoginFailed : Event
    }

    fun onEmailChange(email: String) = viewModelScope.launch {
        updateUiState { state -> state.copy(email = email) }
    }

    fun onPassChange(pass: String) = viewModelScope.launch {
        updateUiState { state -> state.copy(pass = pass) }
        checkInvalid()
    }

    fun onUpdateTextFiledFocus(isFocus: Boolean) = viewModelScope.launch {
        updateUiState { state -> state.copy(isTextFieldFocused = isFocus) }
        checkInvalid()
    }

    private fun checkInvalid() {
        if (uiState.email.length < 6 || uiState.pass.length < 6) {
            asyncUpdateUiState(viewModelScope) { state -> state.copy(isInValid = true) }
        } else {
            asyncUpdateUiState(viewModelScope) { state -> state.copy(isInValid = false) }
        }
    }

    fun onSubmitLogin(email: String, pass: String) = viewModelScope.launch {
        mainRepository.user.collect { user ->
            showLoading()
            if (user.email == email && user.password == pass) {
                mainRepository.setIsLogin(true)
                sendEvent(Event.LoginSuccess)
                hideLoading()
            } else {
                sendEvent(Event.LoginFailed)
                hideLoading()
            }
        }
    }

    fun navigateRegister() {
        appNavigator.navigateTo(AppDestination.Register())
    }

    fun onForgetPassword() {
        appNavigator.showSettingDialog(true)
    }

    fun navigateMovies() {
        appNavigator.navigateTo(
            route = AppDestination.MovieList(),
            popUpToRoute = AppDestination.Login.route,
            isInclusive = true,
        )
    }
}
