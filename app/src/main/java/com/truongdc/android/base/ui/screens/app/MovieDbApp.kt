package com.truongdc.android.base.ui.screens.app

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.common.extensions.showToast
import com.truongdc.android.base.navigation.AppNavigator
import com.truongdc.android.base.navigation.MovieNavHost
import com.truongdc.android.base.navigation.NavigationIntent
import com.truongdc.android.base.ui.screens.setting.SettingsDialog
import kotlinx.coroutines.flow.receiveAsFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDbApp(
    navigator: AppNavigator,
    viewModel: MovieDbAppViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    UiStateContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = {},
    ) { uiState ->
        if (uiState.isShowSettingDialog) {
            SettingsDialog(
                onLogout = {
                    viewModel.logoutAccount()
                },
                onDismiss = {
                    viewModel.displaySettingDialog(false)
                })
        }
        NavigationIntentListener(navigator, snackbarHostState)
        LaunchedEffect(Unit) {
            navigator.showSnackBar(
                message = "Welcome to MovieDb",
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {
            MovieNavHost(navigator.navController)
        }
    }
}

@Composable
private fun NavigationIntentListener(
    navigator: AppNavigator,
    snackbarHostState: SnackbarHostState,
    viewModel: MovieDbAppViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = (context as? Activity)
    LaunchedEffect(Unit) {
        navigator.navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) return@collect
            when (intent) {
                is NavigationIntent.ShowToast -> {
                    context.showToast(intent.message)
                }

                is NavigationIntent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = intent.message,
                        actionLabel = intent.actionLabel,
                        withDismissAction = intent.withDismissAction,
                        duration = intent.duration
                    )
                }

                is NavigationIntent.ShowSettingDialog -> {
                    viewModel.displaySettingDialog(intent.isShowDialog)
                }

                else -> {}
            }
        }
    }
}
