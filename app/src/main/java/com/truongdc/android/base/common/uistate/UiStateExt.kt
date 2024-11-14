package com.truongdc.android.base.common.uistate

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.truongdc.android.base.base.state.UiStateDelegate
import com.truongdc.android.base.data.remote.error.ErrorResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

inline fun <T> Fragment.uiStateDiffRender(
    init: UiStateDiffRender.Builder<T>.() -> Unit
): UiStateDiffRender<T> {

    var render: UiStateDiffRender<T>? = null

    lifecycle.addObserver(object : DefaultLifecycleObserver {
        val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
            val viewLifecycleOwner = it ?: return@Observer

            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    render?.clear()
                }
            })
        }

        override fun onCreate(owner: LifecycleOwner) {
            viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
            render = null
        }
    })

    return UiStateDiffRender.Builder<T>()
        .apply(init)
        .build().apply {
            render = this
        }
}

fun <State, Event> UiStateDelegate<State, Event>.render(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    render: UiStateDiffRender<State>
): Job = lifecycleOwner.lifecycleScope.launch {
    uiStateFlow.flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = lifecycleState,
    ).collectLatest(render::render)
}

fun <State, Event> UiStateDelegate<State, Event>.collectEvent(
    lifecycle: Lifecycle,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (event: Event) -> Unit
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
    block: (event: Event) -> Unit
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
    block: (Boolean) -> Unit
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
    block: (Throwable) -> Unit
): Job = lifecycle.coroutineScope.launch {
    error.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = lifecycleState,
    ).collect {
        block(it)
    }
}

fun <State, Event> UiStateDelegate<State, Event>.collectErrorResponse(
    lifecycle: Lifecycle,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (ErrorResponse) -> Unit
): Job = lifecycle.coroutineScope.launch {
    errorResponse.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = lifecycleState,
    ).collect {
        block(it)
    }
}
