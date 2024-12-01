package com.truongdc.movie_tmdb.feature.movie_detail

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
import com.truongdc.movie_tmdb.core.common.constant.Constants
import com.truongdc.movie_tmdb.core.designsystem.theme.AppTheme
import com.truongdc.movie_tmdb.core.designsystem.theme.MovieTMDBTheme
import com.truongdc.movie_tmdb.core.model.Movie
import com.truongdc.movie_tmdb.core.ui.UiStateContent

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    UiStateContent(
        uiStateDelegate = viewModel,
        modifier = Modifier,
        onEventEffect = {},
        onDismissErrorDialog = onNavigateBack,
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
    MovieTMDBTheme {
        MovieDetailContent(
            movie = Movie(
                urlImage = ""
            )
        )
    }
}