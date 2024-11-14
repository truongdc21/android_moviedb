package com.truongdc.android.base.ui.screens.movie_detail

import androidx.lifecycle.viewModelScope
import com.truongdc.android.base.base.BaseViewModel
import com.truongdc.android.base.base.state.UiStateDelegate
import com.truongdc.android.base.base.state.UiStateDelegateImpl
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseViewModel(),
    UiStateDelegate<MovieDetailViewModel.UiState, MovieDetailViewModel.Event> by UiStateDelegateImpl(
        UiState()
    ) {

    data class UiState(
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
