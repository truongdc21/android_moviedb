/*
 * Designed and developed by 2024 truongdc21 (Dang Chi Truong)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truongdc.movie.feature.movieDetail

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
import com.truongdc.movie.core.common.constant.Constants
import com.truongdc.movie.core.designsystem.theme.AppTheme
import com.truongdc.movie.core.designsystem.theme.MovieTMDBTheme
import com.truongdc.movie.core.model.Movie
import com.truongdc.movie.core.ui.UiStateContent

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
        },
    )
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
                    .fillMaxWidth(0.4f),
            )
        }
    }
}

@Composable
private fun ImageMovie(urlImage: String, modifier: Modifier) {
    val painter = rememberAsyncImagePainter(
        Constants.BASE_URL_IMAGE + urlImage,
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
    device = "spec:orientation=portrait,width=411dp,height=891dp",
)
private fun Preview() {
    MovieTMDBTheme {
        MovieDetailContent(
            movie = Movie(
                id = 1,
                backDropImage = "",
                overView = "",
                vote = 0.0,
                voteCount = 0,
                title = "Movie 1",
                urlImage = "",
                originalTitle = "",
            ),
        )
    }
}
