package com.truongdc.android.base.ui.screens.movie

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.truongdc.android.base.R
import com.truongdc.android.base.ui.components.ErrorMessage
import com.truongdc.android.base.ui.components.LoadingContent
import com.truongdc.android.base.ui.components.LoadingNextPageItem
import com.truongdc.android.base.ui.components.PageLoader
import com.truongdc.android.base.navigation.AppDestination
import com.truongdc.android.base.navigation.navigate
import com.truongdc.android.base.ui.screens.movie_detail.MovieDetailActivity
import com.truongdc.android.base.resource.theme.AppColors
import com.truongdc.android.base.resource.dimens.DpSize
import com.truongdc.android.base.resource.dimens.SpSize
import com.truongdc.android.base.common.extensions.showToast
import com.truongdc.android.base.common.uistate.collectErrorEffect
import com.truongdc.android.base.common.uistate.collectErrorResponseEffect
import com.truongdc.android.base.common.uistate.collectEvent
import com.truongdc.android.base.common.uistate.collectLoadingWithLifecycle
import com.truongdc.android.base.common.uistate.collectWithLifecycle
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.common.constant.Constants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieListScreen(
    navHostController: NavHostController = rememberNavController(),
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val isLoading by viewModel.collectLoadingWithLifecycle()
    val uiState by viewModel.collectWithLifecycle()
    with(viewModel) {
        collectErrorEffect {
            context.showToast("Throwable: ${it.message}")
        }
        collectErrorResponseEffect {
            context.showToast("ErrorResponse: ${it.messages}")
        }
        collectEvent(lifecycle) { event ->
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
        }
    }
    Scaffold(
        topBar = {
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
                            .clickable {
                                viewModel.onHandleLogOut()
                            }
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(AppColors.Yellow))
        },
    ) {
        LaunchedEffect(key1 = Unit) {
            viewModel.requestMovie()
        }
        LoadingContent(isLoading = isLoading) {
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
    }
}

@Composable
private fun MovieItem(movie: Movie, onClickItem: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = DpSize.dp16, end = DpSize.dp16, top = DpSize.dp8, bottom = DpSize.dp8)
            .background(White)
            .fillMaxWidth(),
        onClick = {
            onClickItem(movie.id)
        },
        colors = CardDefaults.cardColors(
            containerColor = AppColors.BlackCard,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = DpSize.dp8,
            pressedElevation = DpSize.dp10
        )
    ) {
        Column(
            modifier = Modifier.padding(DpSize.dp10)
        ) {
            Row {
                Image(
                    painter = rememberAsyncImagePainter(Constants.BASE_URL_IMAGE + movie.urlImage),
                    contentDescription = null,
                    modifier = Modifier
                        .width(DpSize.dp100)
                        .height(DpSize.dp100)
                        .clip(
                            RoundedCornerShape(DpSize.dp50)
                        )
                        .shadow(elevation = DpSize.dp50, spotColor = AppColors.White),
                    contentScale = ContentScale.FillBounds,
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = DpSize.dp16)
                ) {
                    Text(
                        text = movie.title,
                        fontSize = SpSize.sp16,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.size(DpSize.dp6))
                    Text(
                        text = movie.overView,
                        fontSize = SpSize.sp14,
                        color = White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = movie.vote.toString(),
                            color = White,
                            fontSize = SpSize.sp14,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.size(DpSize.dp6))
                        Image(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}
