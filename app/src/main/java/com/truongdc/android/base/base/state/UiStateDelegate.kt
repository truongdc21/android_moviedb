package com.truongdc.android.base.base.state

import com.truongdc.android.base.data.remote.error.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface UiStateDelegate<UiState, Event> {

    val uiStateFlow: StateFlow<UiState>

    val singleEvents: Flow<Event>

    val isLoading: StateFlow<Boolean>

    val error: Flow<Throwable>

    val errorResponse: Flow<ErrorResponse>

    fun showLoading()

    fun hideLoading()

    suspend fun onSendError(error: Throwable)

    suspend fun onSendErrorResponse(errorResponse: ErrorResponse)

    val UiStateDelegate<UiState, Event>.uiState: UiState

    suspend fun UiStateDelegate<UiState, Event>.updateUiState(
        transform: (uiState: UiState) -> UiState,
    )

    fun UiStateDelegate<UiState, Event>.asyncUpdateUiState(
        coroutineScope: CoroutineScope,
        transform: (uiState: UiState) -> UiState
    ): Job

    suspend fun UiStateDelegate<UiState, Event>.sendEvent(event: Event)
}

/**
 * @param mutexState A mutex for synchronizing state access.
 * @param initialUiState Initial UI state.
 * @param singleLiveEventCapacity Channel capacity for SingleLiveEvent.
 */
class UiStateDelegateImpl<UiState, Event>(
    initialUiState: UiState,
    singleLiveEventCapacity: Int = Channel.BUFFERED,
    private val mutexState: Mutex = Mutex()
) : UiStateDelegate<UiState, Event> {

    private val _uiMutableStateFlow = MutableStateFlow(initialUiState)
    private val _singleEventChannel = Channel<Event>(singleLiveEventCapacity)
    private val _isLoadingStateFlow = MutableStateFlow(false)
    private val _errorChange = Channel<Throwable>(singleLiveEventCapacity)
    private val _errorErrorResponse = Channel<ErrorResponse>(singleLiveEventCapacity)

    override val uiStateFlow: StateFlow<UiState>
        get() = _uiMutableStateFlow.asStateFlow()

    override val singleEvents: Flow<Event>
        get() = _singleEventChannel.receiveAsFlow()

    override val isLoading: StateFlow<Boolean>
        get() = _isLoadingStateFlow

    override val error: Flow<Throwable>
        get() = _errorChange.receiveAsFlow()

    override val errorResponse: Flow<ErrorResponse>
        get() = _errorErrorResponse.receiveAsFlow()

    override fun showLoading() {
        _isLoadingStateFlow.value = true
    }

    override fun hideLoading() {
        _isLoadingStateFlow.value = false
    }

    override suspend fun onSendError(error: Throwable) {
        _errorChange.send(error)
    }

    override suspend fun onSendErrorResponse(errorResponse: ErrorResponse) {
        _errorErrorResponse.send(errorResponse)
    }

    override val UiStateDelegate<UiState, Event>.uiState: UiState
        get() = _uiMutableStateFlow.value

    override suspend fun UiStateDelegate<UiState, Event>.updateUiState(
        transform: (uiState: UiState) -> UiState,
    ) = mutexState.withLock {
        _uiMutableStateFlow.emit(transform(uiState))
    }

    override fun UiStateDelegate<UiState, Event>.asyncUpdateUiState(
        coroutineScope: CoroutineScope,
        transform: (uiState: UiState) -> UiState
    ) = coroutineScope.launch {
        updateUiState { state -> transform(state) }
    }

    override suspend fun UiStateDelegate<UiState, Event>.sendEvent(event: Event) =
        _singleEventChannel.send(event)
}