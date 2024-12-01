package com.truongdc.movie_tmdb.core.viewmodel

import com.truongdc.movie_tmdb.core.state.CombinedStateDelegate

abstract class CombinedStateViewModel<UiState, State, Event>(
    combinedStateDelegate: CombinedStateDelegate<UiState, State, Event>,
) : BaseViewModel<UiState, Event>(),
    CombinedStateDelegate<UiState, State, Event> by combinedStateDelegate


