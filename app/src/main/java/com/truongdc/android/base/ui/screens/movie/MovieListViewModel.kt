package com.truongdc.android.base.ui.screens.movie

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.truongdc.android.base.base.BaseViewModel
import com.truongdc.android.base.base.state.UiStateDelegate
import com.truongdc.android.base.base.state.UiStateDelegateImpl
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.data.repository.MovieRepository
import com.truongdc.android.base.data.local.datastores.PreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val preferencesDataStore: PreferencesDataStore
) : BaseViewModel(),
    UiStateDelegate<MovieListViewModel.UiState, MovieListViewModel.Event> by UiStateDelegateImpl(
        UiState()
    ) {

    data class UiState(
        val flowPagingMovie: Flow<PagingData<Movie>>? = null
    )

    sealed interface Event {
        data object LogOutSuccess : Event

        data object LogOutFailed : Event
    }

    fun requestMovie() {
        launchTaskSync(onRequest = {
            movieRepository.getMovies()
        }, onSuccess = { mFlowPagingMovie ->
            asyncUpdateUiState(viewModelScope) { state -> state.copy(flowPagingMovie = mFlowPagingMovie) }
        })
    }

    fun onHandleLogOut() = viewModelScope.launch {
        try {
            showLoading()
            delay(2000)
            preferencesDataStore.setIsLogIn(false)
            sendEvent(Event.LogOutSuccess)
            hideLoading()
        } catch (e: Exception) {
            e.printStackTrace()
            hideLoading()
            sendEvent(Event.LogOutFailed)
        }
    }
}

