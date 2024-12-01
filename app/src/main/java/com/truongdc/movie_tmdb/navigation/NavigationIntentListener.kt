package com.truongdc.movie_tmdb.navigation

import android.app.Activity
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.truongdc.movie_tmdb.core.navigation.AppNavigator
import com.truongdc.movie_tmdb.core.navigation.NavigationIntent
import com.truongdc.movie_tmdb.core.ui.extensions.showToast
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun NavigationIntentListener(
    navigator: AppNavigator,
    snackbarHostState: SnackbarHostState,
    showSettingDialog: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val activity = (context as? Activity)
    LaunchedEffect(activity) {
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
                        duration = SnackbarDuration.Short
                    )
                }

                is NavigationIntent.ShowSettingDialog -> {
                    showSettingDialog(intent.isShowDialog)
                }

                is NavigationIntent.ShowForceUpdate -> {

                }

                is NavigationIntent.ShowMaintenanceMode -> {

                }

                is NavigationIntent.ShowLogoutDialog -> {

                }
            }
        }
    }
}
