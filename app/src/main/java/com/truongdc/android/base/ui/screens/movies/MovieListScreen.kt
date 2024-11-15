package com.truongdc.android.base.ui.screens.movies

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.truongdc.android.base.R
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.common.extensions.showToast
import com.truongdc.android.base.navigation.AppDestination
import com.truongdc.android.base.navigation.navigate
import com.truongdc.android.base.resource.dimens.DpSize
import com.truongdc.android.base.resource.dimens.SpSize
import com.truongdc.android.base.resource.theme.AppColors
import com.truongdc.android.base.ui.components.ErrorMessage
import com.truongdc.android.base.ui.components.LoadingNextPageItem
import com.truongdc.android.base.ui.components.PageLoader
import com.truongdc.android.base.ui.screens.movie_detail.MovieDetailActivity
import com.truongdc.android.base.ui.screens.movies.components.MovieItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieListScreen(
    navHostController: NavHostController = rememberNavController(),
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    UiStateContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = { event ->
            when (event) {
                is MovieListViewModel.Event.LogOutSuccess -> {
                    context.showToast("Logout Success!")
                    navHostController.navigate(AppDestination.Splash) {
                        popUpTo(AppDestination.MovieList.route) { inclusive = true }
                    }
                }

                is MovieListViewModel.Event.LogOutFailed -> {
                    context.showToast("LogOut Failed, Please try again!")
                }
            }
        }, content = { uiState ->
            Scaffold(
                topBar = {
                    MovieTopBar {
                        viewModel.onHandleLogOut()
                    }
                }
            ) {
                LaunchedEffect(key1 = Unit) { viewModel.requestMovie() }
                uiState.flowPagingMovie?.let { pagingData ->
                    val pagingItems = pagingData.collectAsLazyPagingItems()
                    LazyColumn(modifier = Modifier.padding(it)) {
                        item { Spacer(modifier = Modifier.padding(5.dp)) }
                        items(pagingItems.itemCount) { index ->
                            MovieItem(movie = pagingItems[index]!!, onClickItem = { movieId ->
                                val intent = Intent(context, MovieDetailActivity::class.java)
                                intent.putExtra("MOVIE_ID", movieId)
                                context.startActivity(intent)
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
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieTopBar(onTapSignOut: () -> Unit) {
    TopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_movie),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(color = AppColors.Black),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = DpSize.dp8, start = DpSize.dp16)
                    .width(DpSize.dp30)
                    .height(DpSize.dp30)
            )
            Text(
                text = "MOVIE APP",
                fontSize = SpSize.sp18,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_logout),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(color = AppColors.Indigo),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = DpSize.dp24)
                    .clickable { onTapSignOut.invoke() }
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(AppColors.Yellow))
}
