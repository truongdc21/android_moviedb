package com.truongdc.movie_tmdb.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truongdc.movie_tmdb.core.common.result.DataResult
import com.truongdc.movie_tmdb.core.navigation.AppNavigator
import com.truongdc.movie_tmdb.core.state.UiStateDelegate
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