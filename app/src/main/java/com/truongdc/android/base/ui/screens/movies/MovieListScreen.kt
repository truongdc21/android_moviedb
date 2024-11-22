package com.truongdc.android.base.ui.screens.movies

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.resource.dimens.Orientation
import com.truongdc.android.base.resource.dimens.isGreaterThanCompact
import com.truongdc.android.base.resource.dimens.isGreaterThanMedium
import com.truongdc.android.base.resource.theme.AppTheme
import com.truongdc.android.base.ui.components.ErrorMessage
import com.truongdc.android.base.ui.components.LoadingNextPageItem
import com.truongdc.android.base.ui.components.PageLoader
import com.truongdc.android.base.ui.screens.movies.components.MovieItem

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    UiStateContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = {}
    ) { uiState ->
        Scaffold(
            topBar = {
                if (AppTheme.orientation == Orientation.Portrait) {
                    MovieTopBar { viewModel.showSettingDialog() }
                }
            },
            floatingActionButton = {
                if (AppTheme.orientation == Orientation.Landscape) {
                    FloatingActionButton(
                        onClick = {
                            viewModel.showSettingDialog()
                        },
                        content = { Icon(Icons.Filled.Settings, contentDescription = null) }
                    )
                }
            }
        ) { paddingValues ->
            MoviesContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onTapMovie = viewModel::navigateToMovieDetail
            )
        }
    }
}

@Composable
private fun MoviesContent(
    paddingValues: PaddingValues,
    uiState: MovieListViewModel.UiState,
    onTapMovie: (Int) -> Unit,
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
                text = "MOVIE APP",
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
