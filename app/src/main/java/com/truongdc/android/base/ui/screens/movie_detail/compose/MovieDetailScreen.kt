package com.truongdc.android.base.ui.screens.movie_detail.compose

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.common.constant.Constants
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.resource.dimens.isPortrait
import com.truongdc.android.base.resource.theme.AppTheme
import com.truongdc.android.base.resource.theme.MovieDbTheme
import com.truongdc.android.base.ui.screens.movie_detail.MovieDetailViewModel

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    UiStateContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = {},
        content = { uiState ->
            uiState.movie?.let {
                MovieDetailContent(uiState.movie)
            }
        })
}

@Composable
private fun MovieDetailContent(movie: Movie) {
    if (AppTheme.orientation.isPortrait()) {
        Column(Modifier.fillMaxSize()) {
            ImageMovie(
                urlImage = movie.urlImage,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.4f),
            )
        }
    } else {
        Row(Modifier.fillMaxSize()) {
            ImageMovie(
                urlImage = movie.urlImage,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(0.4f)
            )
        }
    }
}

@Composable
private fun ImageMovie(urlImage: String, modifier: Modifier) {
    val painter = rememberAsyncImagePainter(
        Constants.BASE_URL_IMAGE + urlImage
    )
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = "spec:orientation=portrait,width=411dp,height=891dp"
)
private fun Preview() {
    MovieDbTheme {
        MovieDetailContent(
            movie = Movie(
                urlImage = ""
            )
        )
    }
}