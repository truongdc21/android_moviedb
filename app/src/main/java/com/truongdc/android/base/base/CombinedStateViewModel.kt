package com.truongdc.android.base.base

import com.truongdc.android.base.base.state.CombinedStateDelegate

abstract class CombinedStateViewModel<UiState, State, Event>(
    combinedStateDelegate: CombinedStateDelegate<UiState, State, Event>
) : BaseViewModel<UiState, Event>(),
    CombinedStateDelegate<UiState, State, Event> by combinedStateDelegate


