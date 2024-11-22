package com.truongdc.android.base.ui.screens.app

import androidx.lifecycle.viewModelScope
import com.truongdc.android.base.base.UiStateViewModel
import com.truongdc.android.base.base.state.UiStateDelegateImpl
import com.truongdc.android.base.data.local.datastores.PreferencesDataStore
import com.truongdc.android.base.navigation.AppDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
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
        val isShowSettingDialog: Boolean = false,
        val isLogin: Boolean? = null,
    )

    sealed interface Event

    init {
        viewModelScope.launch {
            val isLogin = preferencesDataStore.isLogIn.first()
            updateUiState { state -> state.copy(isLogin = isLogin) }
        }
    }

    fun showSettingDialog(isShow: Boolean) {
        asyncUpdateUiState(viewModelScope) { state -> state.copy(isShowSettingDialog = isShow) }
    }

    fun logoutAccount() {
        viewModelScope.launch {
            try {
                showLoading()
                setLogout()
                appNavigator.showToast("Logout success!")
                popToLogin()
                hideLoading()
            } catch (e: Exception) {
                e.printStackTrace()
                appNavigator.showToast("Logout failed!")
                hideLoading()
            }
        }
    }

    private suspend fun setLogout() = preferencesDataStore.setIsLogIn(false)

    private fun popToLogin() {
        appNavigator.navigateTo(
            route = AppDestination.Login.route,
            popUpToRoute = appNavigator.currentRoute(),
            isInclusive = true
        )
    }
}
