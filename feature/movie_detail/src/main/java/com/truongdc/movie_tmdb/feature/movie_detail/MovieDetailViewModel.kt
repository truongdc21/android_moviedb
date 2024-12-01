package com.truongdc.movie_tmdb.feature.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.truongdc.movie_tmdb.core.data.repository.MovieRepository
import com.truongdc.movie_tmdb.core.model.Movie
import com.truongdc.movie_tmdb.core.navigation.AppDestination
import com.truongdc.movie_tmdb.core.state.UiStateDelegateImpl
import com.truongdc.movie_tmdb.core.viewmodel.UiStateViewModel
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

    private fun requestMovie(movieId: Int) {
        launchTaskSync(isLoading = true, onRequest = {
            movieRepository.getDetailMovies(movieId)
        }, onSuccess = { movie ->
            asyncUpdateUiState(viewModelScope) { state -> state.copy(movie = movie) }
        })
    }

    fun sendEvents(event: Event) = viewModelScope.launch { sendEvent(event) }
}
