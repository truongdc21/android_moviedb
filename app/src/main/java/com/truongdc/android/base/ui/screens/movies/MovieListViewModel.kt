package com.truongdc.android.base.ui.screens.movies

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.truongdc.android.base.base.CombinedStateViewModel
import com.truongdc.android.base.base.state.CombinedStateDelegateImpl
import com.truongdc.android.base.base.state.asyncUpdateInternalState
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.data.repository.MovieRepository
import com.truongdc.android.base.navigation.AppDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
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
            movieRepository.getMovies()
        }, onSuccess = { mFlowPagingMovie ->
            asyncUpdateInternalState { state -> state.copy(flowPagingMovie = mFlowPagingMovie) }
        })
    }

    fun navigateToMovieDetail(movieId: Int) {
        appNavigator.navigateTo(
            route = AppDestination.MovieDetail(movieId.toString())
        )
    }

    fun showSettingDialog(isShowDialog: Boolean = true) {
        appNavigator.showSettingDialog(isShowDialog)
    }
}
