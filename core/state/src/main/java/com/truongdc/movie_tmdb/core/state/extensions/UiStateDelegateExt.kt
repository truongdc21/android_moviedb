package com.truongdc.movie_tmdb.core.state.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.truongdc.movie_tmdb.core.state.UiStateDelegate
import kotlinx.coroutines.flow.FlowCollector


@Composable
fun <R> UiStateDelegate<R, *>.collectUiState() = this.uiStateFlow.collectAsState()

@Composable
fun <R> UiStateDelegate<R, *>.collectWithLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) = this.uiStateFlow.collectAsStateWithLifecycle(
    minActiveState = minActiveState,
)

@Composable
fun <R> UiStateDelegate<R, *>.collectLoadingWithLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
) = this.isLoading.collectAsStateWithLifecycle(
    minActiveState = minActiveState,
)

@Composable
fun <State, Event> UiStateDelegate<State, Event>.collectEventEffect(
    lifecycleOwner: LifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    vararg keys: Any?,
    collector: FlowCollector<Event>,
) = LaunchedEffect(Unit, *keys) {
    singleEvents.flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = lifecycleState,
    ).collect(collector)
}

@Composable
fun UiStateDelegate<*, *>.collectErrorEffect(
    lifecycleOwner: LifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    vararg keys: Any?,
    collector: FlowCollector<Throwable>,
) = LaunchedEffect(Unit, *keys) {
    error.flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = lifecycleState,
    ).collect(collector)
}
