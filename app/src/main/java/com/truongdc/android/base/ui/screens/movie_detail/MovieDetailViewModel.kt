package com.truongdc.android.base.ui.screens.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.truongdc.android.base.base.UiStateViewModel
import com.truongdc.android.base.base.state.UiStateDelegateImpl
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.data.repository.MovieRepository
import com.truongdc.android.base.navigation.AppDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val stateHandle: SavedStateHandle,
) : UiStateViewModel<MovieDetailViewModel.UiState, MovieDetailViewModel.Event>(
    UiStateDelegateImpl(
        UiState()
    )
) {
    init {
        asyncUpdateUiState(viewModelScope) { state ->
            state.copy(movieId = AppDestination.MovieDetail.getMovieId(stateHandle))
        }
        if (uiState.movieId.isNotBlank()) {
            requestMovie(uiState.movieId.toInt())
        }
    }

    data class UiState(
        val movieId: String = "",
        val movie: Movie? = null
    )

    sealed interface Event {
        data object BackToList : Event
    }

    fun requestMovie(movieId: Int) {
        launchTaskSync(isLoading = true, onRequest = {
            movieRepository.getDetailMovies(movieId)
        }, onSuccess = { movie ->
            asyncUpdateUiState(viewModelScope) { state -> state.copy(movie = movie) }
        })
    }

    fun sendEvents(event: Event) = viewModelScope.launch { sendEvent(event) }
}
