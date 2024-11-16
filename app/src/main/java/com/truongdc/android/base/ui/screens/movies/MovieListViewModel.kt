package com.truongdc.android.base.ui.screens.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.truongdc.android.base.base.CombinedStateViewModel
import com.truongdc.android.base.base.state.CombinedStateDelegateImpl
import com.truongdc.android.base.data.local.datastores.PreferencesDataStore
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.data.repository.MovieRepository
import com.truongdc.android.base.navigation.AppDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val preferencesDataStore: PreferencesDataStore
) : CombinedStateViewModel<MovieListViewModel.UiState, MovieListViewModel.State, MovieListViewModel.Event>(
    combinedStateDelegate = CombinedStateDelegateImpl(
        initialState = State(),
        initialUiState = UiState()
    )
) {

    data class UiState(
        val flowPagingMovie: Flow<PagingData<Movie>>? = null
    )

    data class State(val movie: Movie? = null)

    sealed interface Event {
        data object LogOutSuccess : Event

        data object LogOutFailed : Event
    }


    init {
        requestMovie()
    }
    private fun requestMovie() {
        launchTaskSync(onRequest = {
            internalState
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

    fun popToSplash() {
        navigator.navigateTo(
            route = AppDestination.Splash(),
            popUpToRoute = AppDestination.MovieList.route,
            isInclusive = true
        )
    }

    fun navigateToMovieDetail(movieId: String) {
        navigator.navigateTo(
            route = AppDestination.MovieDetail(movieId)
        )
    }
}

