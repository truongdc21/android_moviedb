package com.truongdc.android.base.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truongdc.android.base.base.state.UiStateDelegate
import com.truongdc.android.base.data.remote.error.ErrorResponse
import com.truongdc.android.base.data.base.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class UiStateViewModel<UiState, Event>(
    uiStateDelegate: UiStateDelegate<UiState, Event>,
) : BaseViewModel<UiState, Event>(), UiStateDelegate<UiState, Event> by uiStateDelegate
