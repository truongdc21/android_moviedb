package com.truongdc.android.base.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.truongdc.android.base.base.BaseViewModel
import com.truongdc.android.base.base.state.UiStateDelegate
import com.truongdc.android.base.base.state.UiStateDelegateImpl
import com.truongdc.android.base.data.local.datastores.PreferencesDataStore
import com.truongdc.android.base.data.local.datastores.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val preferencesDataStore: PreferencesDataStore
) : BaseViewModel(),
    UiStateDelegate<LoginViewModel.UiState, LoginViewModel.Event> by UiStateDelegateImpl(
        UiState()
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
        userDataStore.getUser().collect { user ->
            showLoading()
            if (user.email == email && user.password == pass) {
                preferencesDataStore.setIsLogIn(true)
                delay(2000)
                sendEvent(Event.LoginSuccess)
                hideLoading()
            } else {
                delay(2000)
                sendEvent(Event.LoginFailed)
                hideLoading()
            }
        }
    }
}
