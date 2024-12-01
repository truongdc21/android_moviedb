package com.truongdc.movie_tmdb.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.truongdc.movie_tmdb.core.designsystem.components.LoadingContent
import com.truongdc.movie_tmdb.core.state.UiStateDelegate
import com.truongdc.movie_tmdb.core.state.extensions.collectEvent
import com.truongdc.movie_tmdb.core.state.extensions.collectLoadingWithLifecycle
import com.truongdc.movie_tmdb.core.state.extensions.collectWithLifecycle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <UiState, Event> UiStateContent(
    viewModel: UiStateDelegate<UiState, Event>,
    modifier: Modifier = Modifier,
    onEventEffect: (event: Event) -> Unit = {},
    content: @Composable (uiState: UiState) -> Unit,
) {
    viewModel.apply {
        val uiState by collectWithLifecycle()
        val isLoading by collectLoadingWithLifecycle()
        collectEvent(LocalLifecycleOwner.current) { event -> onEventEffect(event) }
        LoadingContent(
            isLoading = isLoading,
            modifier
        ) {
            content(uiState)
        }
    }
}
