package com.truongdc.movie_tmdb.feature.movie_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.truongdc.movie_tmdb.core.designsystem.R.string
import com.truongdc.movie_tmdb.core.designsystem.components.ErrorMessage
import com.truongdc.movie_tmdb.core.designsystem.components.LoadingNextPageItem
import com.truongdc.movie_tmdb.core.designsystem.components.PageLoader
import com.truongdc.movie_tmdb.core.designsystem.dimens.Orientation
import com.truongdc.movie_tmdb.core.designsystem.theme.AppTheme
import com.truongdc.movie_tmdb.core.model.Movie
import com.truongdc.movie_tmdb.core.ui.UiStateContent
import com.truongdc.movie_tmdb.feature.movie_list.components.MovieItem

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    onNavigateToDetail: (Movie) -> Unit = {},
    onShowSettingDialog: () -> Unit = {},
) {
    UiStateContent(
        uiStateDelegate = viewModel,
        modifier = Modifier,
        onEventEffect = {}
    ) { uiState ->
        Scaffold(
            topBar = {
                if (AppTheme.orientation == Orientation.Portrait) {
                    MovieTopBar { onShowSettingDialog.invoke() }
                }
            },
            floatingActionButton = {
                if (AppTheme.orientation == Orientation.Landscape) {
                    FloatingActionButton(
                        onClick = { onShowSettingDialog.invoke() },
                        content = { Icon(Icons.Filled.Settings, contentDescription = null) }
                    )
                }
            }
        ) { paddingValues ->
            MoviesContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onTapMovie = onNavigateToDetail
            )
        }
    }
}

@Composable
private fun MoviesContent(
    paddingValues: PaddingValues,
    uiState: MovieListViewModel.UiState,
    onTapMovie: (Movie) -> Unit,
) {

    uiState.flowPagingMovie?.let { pagingData ->
        val pagingItems = pagingData.collectAsLazyPagingItems()
        val countColumns: Int = if (AppTheme.orientation == Orientation.Portrait) 2 else
            if (AppTheme.windowType.isGreaterThanCompact()) 5 else 4
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                state = uiState.lazyGridState,
                columns = GridCells.Fixed(countColumns),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(pagingItems.itemCount) { index ->
                    MovieItem(
                        movie = pagingItems[index]!!,
                        onClickItem = { movieId -> onTapMovie(movieId) }
                    )
                }
                buildLoadState(pagingItems)
            }
        }
    }
}

private fun LazyGridScope.buildLoadState(
    lazyPagingItems: LazyPagingItems<Movie>,
) {
    lazyPagingItems.apply {
        buildRefreshState(loadState.refresh) { retry() }
        buildAppendState(loadState.append) { retry() }
    }
}

private fun LazyGridScope.buildRefreshState(
    loadState: LoadState,
    onRetry: () -> Unit,
) {
    when (loadState) {
        is LoadState.Loading -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                PageLoader()
            }
        }

        is LoadState.Error -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                ErrorMessage(
                    message = loadState.error.localizedMessage ?: "Unknown error",
                    onClickRetry = onRetry
                )
            }
        }

        else -> Unit
    }
}

private fun LazyGridScope.buildAppendState(
    loadState: LoadState,
    onRetry: () -> Unit,
) {
    when (loadState) {
        is LoadState.Loading -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LoadingNextPageItem(modifier = Modifier)
            }
        }

        is LoadState.Error -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                ErrorMessage(
                    message = loadState.error.localizedMessage ?: "Unknown error",
                    onClickRetry = onRetry
                )
            }
        }

        else -> Unit
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieTopBar(showSetting: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = string.movie_app).uppercase(),
                style = AppTheme.styles.titleMedium,
                color = AppTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
            )
        },
        actions = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = null,
                tint = AppTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clickable {
                        showSetting.invoke()
                    })
        },
        colors = TopAppBarDefaults.topAppBarColors(AppTheme.colors.primary)
    )
}
