package com.truongdc.android.base.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.truongdc.android.base.base.uistate.collectEvent
import com.truongdc.android.base.base.uistate.collectLoadingWithLifecycle
import com.truongdc.android.base.base.uistate.collectWithLifecycle
import com.truongdc.android.base.ui.components.LoadingContent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <UiState, Event> ScreenContent(
    viewModel: BaseViewModel<UiState, Event>,
    modifier: Modifier = Modifier,
    onEventEffect: (event: Event) -> Unit = {},
    content: @Composable (uiState: UiState) -> Unit
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
