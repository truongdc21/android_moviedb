package com.truongdc.android.base.ui.screens.movie_detail.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.ui.screens.movie_detail.MovieDetailViewModel


@Composable
fun MovieDetailScreen(viewModel: MovieDetailViewModel = hiltViewModel()) {
    UiStateContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = {

        }) {
        Text(text = "${it.movie?.title}")
    }
}