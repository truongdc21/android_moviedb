package com.truongdc.android.base.base

import com.truongdc.android.base.base.state.UiStateDelegate

abstract class UiStateViewModel<UiState, Event>(
    uiStateDelegate: UiStateDelegate<UiState, Event>,
) : BaseViewModel<UiState, Event>(), UiStateDelegate<UiState, Event> by uiStateDelegate
