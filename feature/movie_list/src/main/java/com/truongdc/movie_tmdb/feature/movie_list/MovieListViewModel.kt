package com.truongdc.movie_tmdb.feature.movie_list

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.truongdc.movie_tmdb.core.data.repository.MovieRepository
import com.truongdc.movie_tmdb.core.model.Movie
import com.truongdc.movie_tmdb.core.state.CombinedStateDelegateImpl
import com.truongdc.movie_tmdb.core.state.asyncUpdateInternalState
import com.truongdc.movie_tmdb.core.viewmodel.CombinedStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : CombinedStateViewModel<MovieListViewModel.UiState, MovieListViewModel.State, MovieListViewModel.Event>(
    combinedStateDelegate = CombinedStateDelegateImpl(
        initialState = State(),
        initialUiState = UiState()
    )
) {

    data class UiState(
        val flowPagingMovie: Flow<PagingData<Movie>>? = null,
        val lazyGridState: LazyGridState = LazyGridState()
    )

    data class State(val flowPagingMovie: Flow<PagingData<Movie>>? = null)

    sealed interface Event

    init {
        requestMovie()
        collectUpdateUiState(viewModelScope) { state, uiState ->
            uiState.copy(flowPagingMovie = state.flowPagingMovie?.cachedIn(viewModelScope))
        }
    }

    private fun requestMovie() {
        launchTaskSync(onRequest = {
            internalState
            movieRepository.fetchMovies()
        }, onSuccess = { mFlowPagingMovie ->
            asyncUpdateInternalState { state -> state.copy(flowPagingMovie = mFlowPagingMovie) }
        })
    }
}
