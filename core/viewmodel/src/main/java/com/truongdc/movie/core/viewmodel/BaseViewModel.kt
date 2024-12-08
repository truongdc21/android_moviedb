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
package com.truongdc.movie.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truongdc.movie.core.common.result.DataResult
import com.truongdc.movie.core.navigation.AppNavigator
import com.truongdc.movie.core.state.UiStateDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel<UiState, Event> : ViewModel() {

    @Inject
    lateinit var appNavigator: AppNavigator

    fun <T> UiStateDelegate<UiState, Event>.launchTaskSync(
        isLoading: Boolean = false,
        onRequest: suspend CoroutineScope.() -> DataResult<T>,
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onCompletion: () -> Unit = {},
    ) = viewModelScope.launch {
        if (isLoading) showLoading()
        when (val asynchronousTasks = onRequest(this)) {
            is DataResult.Success -> onSuccess(asynchronousTasks.data)
            is DataResult.Error -> {
                val throwable = asynchronousTasks.throwable
                onError(throwable)
                onSendError(throwable)
            }

            is DataResult.Loading -> {}
        }
    }.also { job ->
        job.invokeOnCompletion {
            onCompletion()
            if (isLoading) hideLoading()
        }
    }
}
