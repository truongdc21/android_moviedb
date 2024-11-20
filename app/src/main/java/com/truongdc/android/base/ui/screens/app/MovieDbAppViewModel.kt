package com.truongdc.android.base.ui.screens.app

import androidx.lifecycle.viewModelScope
import com.truongdc.android.base.base.UiStateViewModel
import com.truongdc.android.base.base.state.UiStateDelegateImpl
import com.truongdc.android.base.data.local.datastores.PreferencesDataStore
import com.truongdc.android.base.navigation.AppDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDbAppViewModel @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) :
    UiStateViewModel<MovieDbAppViewModel.UiState, MovieDbAppViewModel.Event>(
        UiStateDelegateImpl(UiState())
    ) {

    data class UiState(
        val isShowSettingDialog: Boolean = false
    )

    sealed interface Event

    fun displaySettingDialog(isShowDialog: Boolean) = asyncUpdateUiState(viewModelScope) { state ->
        state.copy(isShowSettingDialog = isShowDialog)
    }

    fun logoutAccount() {
        viewModelScope.launch {
            try {
                showLoading()
                setLogout()
                navigator.showToast("Logout success!")
                popToLogin()
                hideLoading()
            } catch (e: Exception) {
                e.printStackTrace()
                navigator.showToast("Logout failed!")
                hideLoading()
            }
        }
    }

    private suspend fun setLogout() = preferencesDataStore.setIsLogIn(false)

    private fun popToLogin() {
        navigator.navigateTo(
            route = AppDestination.Login.route,
            popUpToRoute = navigator.currentRoute(),
            isInclusive = true
        )
    }
}
