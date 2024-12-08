/*
 * Designed and developed by 2024 truongdc21 (Dang Chi Truong)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truongdc.movie.ui

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.truongdc.movie.core.data.repository.MainRepository
import com.truongdc.movie.core.designsystem.R.string
import com.truongdc.movie.core.state.UiStateDelegateImpl
import com.truongdc.movie.core.viewmodel.UiStateViewModel
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
        UiStateDelegateImpl(UiState()),
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
