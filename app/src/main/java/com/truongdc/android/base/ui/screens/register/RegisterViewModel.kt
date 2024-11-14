package com.truongdc.android.base.ui.screens.register

import androidx.lifecycle.viewModelScope
import com.truongdc.android.base.base.BaseViewModel
import com.truongdc.android.base.base.state.UiStateDelegate
import com.truongdc.android.base.base.state.UiStateDelegateImpl
import com.truongdc.android.base.data.local.datastores.UserDataStore
import com.truongdc.android.base.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
) : BaseViewModel(),
    UiStateDelegate<RegisterViewModel.UiState, RegisterViewModel.Event> by UiStateDelegateImpl(
        UiState()
    ) {
    data class UiState(
        val name: String = "",
        val email: String = "",
        val pass: String = "",
        val isTextFieldFocused: Boolean = false,
        val isInValid: Boolean = true,
    )

    sealed interface Event {
        data object RegisterSuccess : Event

        data object RegisterFailed : Event
    }

    fun onNameChange(name: String) = viewModelScope.launch {
        updateUiState { state -> state.copy(name = name) }
        checkInvalid()
    }

    fun onEmailChange(email: String) = viewModelScope.launch {
        updateUiState { state -> state.copy(email = email) }
        checkInvalid()
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
        if (uiState.email.length < 6 || uiState.pass.length < 6 || uiState.name.length < 2) {
            asyncUpdateUiState(viewModelScope) { state -> state.copy(isInValid = true) }
        } else {
            asyncUpdateUiState(viewModelScope) { state -> state.copy(isInValid = false) }
        }
    }

    fun onSubmitRegister(user: User) {
        viewModelScope.launch {
            try {
                showLoading()
                delay(2000)
                userDataStore.saveUser(user)
                sendEvent(Event.RegisterSuccess)
                hideLoading()
            } catch (e: Exception) {
                onSendError(e)
                hideLoading()
            }
        }
    }
}

