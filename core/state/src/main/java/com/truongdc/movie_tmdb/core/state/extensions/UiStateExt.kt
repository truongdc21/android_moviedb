package com.truongdc.movie_tmdb.core.state.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.truongdc.movie_tmdb.core.state.UiStateDelegate
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun <State, Event> UiStateDelegate<State, Event>.collectEvent(
    lifecycle: Lifecycle,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (event: Event) -> Unit,
): Job = lifecycle.coroutineScope.launch {
    singleEvents.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = lifecycleState,
    ).collect {
        block(it)
    }
}

fun <State, Event> UiStateDelegate<State, Event>.collectEvent(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (event: Event) -> Unit,
): Job = lifecycleOwner.lifecycleScope.launch {
    singleEvents.flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = lifecycleState,
    ).collect { event ->
        block.invoke(event)
    }
}

fun <State, Event> UiStateDelegate<State, Event>.collectLoading(
    lifecycle: Lifecycle,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (Boolean) -> Unit,
): Job = lifecycle.coroutineScope.launch {
    isLoading.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = lifecycleState,
    ).collect {
        block(it)
    }
}

fun <State, Event> UiStateDelegate<State, Event>.collectError(
    lifecycle: Lifecycle,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (Throwable) -> Unit,
): Job = lifecycle.coroutineScope.launch {
    error.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = lifecycleState,
    ).collect {
        block(it)
    }
}
