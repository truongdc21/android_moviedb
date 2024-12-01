package com.truongdc.movie_tmdb.core.viewmodel

import com.truongdc.movie_tmdb.core.state.UiStateDelegate


abstract class UiStateViewModel<UiState, Event>(
    uiStateDelegate: UiStateDelegate<UiState, Event>,
) : BaseViewModel<UiState, Event>(), UiStateDelegate<UiState, Event> by uiStateDelegate
