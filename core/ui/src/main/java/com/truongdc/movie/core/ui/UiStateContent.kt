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
package com.truongdc.movie.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.truongdc.movie.core.designsystem.components.LoadingContent
import com.truongdc.movie.core.state.UiStateDelegate
import com.truongdc.movie.core.state.extensions.collectErrorEffect
import com.truongdc.movie.core.state.extensions.collectEventEffect
import com.truongdc.movie.core.state.extensions.collectLoadingWithLifecycle
import com.truongdc.movie.core.state.extensions.collectWithLifecycle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <UiState, Event> UiStateContent(
    uiStateDelegate: UiStateDelegate<UiState, Event>,
    modifier: Modifier = Modifier,
    onEventEffect: (event: Event) -> Unit = {},
    onErrorEffect: (error: Throwable) -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
    content: @Composable (uiState: UiState) -> Unit,
) {
    val uiState by uiStateDelegate.collectWithLifecycle()
    val isLoading by uiStateDelegate.collectLoadingWithLifecycle()
    val (errorDialogVisible, setErrorDialogVisible) = remember { mutableStateOf(false) }
    val currentError = remember { mutableStateOf<Throwable?>(null) }

    // Collect events
    uiStateDelegate.collectEventEffect { event ->
        onEventEffect(event)
    }

    // Collect errors and show dialog
    uiStateDelegate.collectErrorEffect { error ->
        currentError.value = error
        setErrorDialogVisible(true)
        onErrorEffect(error)
    }

    // Main content
    LoadingContent(
        isLoading = isLoading,
        modifier = modifier,
    ) {
        content(uiState)
    }

    // ErrorDialog
    if (errorDialogVisible && currentError.value != null) {
        ErrorDialog(
            error = currentError.value!!,
            onDismiss = {
                setErrorDialogVisible(false)
                onDismissErrorDialog()
            },
        )
    }
}

@Composable
private fun ErrorDialog(
    error: Throwable,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Error") },
        text = { Text(text = error.message ?: "An unexpected error occurred.") },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
    )
}
