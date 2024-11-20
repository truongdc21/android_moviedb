package com.truongdc.android.base.ui.screens.movies

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.truongdc.android.base.base.CombinedStateViewModel
import com.truongdc.android.base.base.state.CombinedStateDelegateImpl
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.data.repository.MovieRepository
import com.truongdc.android.base.navigation.AppDestination
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
        val lazyListState: LazyListState = LazyListState(),
        val isShowSettingDialog: Boolean = false,
    )

    data class State(val movie: Movie? = null)

    sealed interface Event

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

    fun navigateToMovieDetail(movieId: String) {
        navigator.navigateTo(
            route = AppDestination.MovieDetail(movieId)
        )
    }
}
