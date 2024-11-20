package com.truongdc.android.base.ui.screens.movies

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.resource.theme.AppColors
import com.truongdc.android.base.ui.components.ErrorMessage
import com.truongdc.android.base.ui.components.LoadingNextPageItem
import com.truongdc.android.base.ui.components.PageLoader
import com.truongdc.android.base.ui.screens.movies.components.MovieItem

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    UiStateContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = {}
    ) { uiState ->
        Scaffold(
            topBar = {
                MovieTopBar { viewModel.navigator.showSettingDialog(isShowDialog = true) }
            }
        ) { paddingValues ->
            MoviesContent(
                paddingValues = paddingValues,
                uiState = uiState,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun MoviesContent(
    paddingValues: PaddingValues,
    uiState: MovieListViewModel.UiState,
    viewModel: MovieListViewModel
) {
    uiState.flowPagingMovie?.let { pagingData ->
        val pagingItems = pagingData.collectAsLazyPagingItems()
        LazyColumn(
            state = uiState.lazyListState,
            modifier = Modifier.padding(paddingValues)
        ) {
            item { Spacer(modifier = Modifier.padding(5.dp)) }
            items(pagingItems.itemCount) { index ->
                MovieItem(movie = pagingItems[index]!!, onClickItem = { movieId ->
                    /*val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra("MOVIE_ID", movieId)
                    context.startActivity(intent)*/
                    viewModel.navigateToMovieDetail(movieId.toString())
                })
            }
            pagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = pagingItems.loadState.refresh as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier.fillParentMaxSize(),
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = pagingItems.loadState.append as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieTopBar(showSetting: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "MOVIE DB",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        },
        actions = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clickable {
                        showSetting.invoke()
                    })
        },
        colors = TopAppBarDefaults.topAppBarColors(AppColors.Yellow)
    )
}
