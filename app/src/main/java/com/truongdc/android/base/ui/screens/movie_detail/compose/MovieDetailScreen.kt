package com.truongdc.android.base.ui.screens.movie_detail.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.common.constant.Constants
import com.truongdc.android.base.ui.screens.movie_detail.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    UiStateContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = {

        }) { uiState ->
        Scaffold {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(Constants.BASE_URL_IMAGE + uiState.movie?.urlImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.5f),
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
}