package com.truongdc.movie_tmdb.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.truongdc.movie_tmdb.R
import com.truongdc.movie_tmdb.core.navigation.AppNavigator
import com.truongdc.movie_tmdb.core.navigation.HandleNavigationIntents
import com.truongdc.movie_tmdb.core.ui.UiStateContent
import com.truongdc.movie_tmdb.core.ui.extensions.showToast
import com.truongdc.movie_tmdb.feature.login.navigation.LoginRouter
import com.truongdc.movie_tmdb.feature.movie_list.navigation.MovieListRouter
import com.truongdc.movie_tmdb.feature.setting.SettingsDialog
import com.truongdc.movie_tmdb.navigation.MovieNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieTMDBApp(
    navigator: AppNavigator,
    viewModel: MovieTMDBAppViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val hasLaunched = rememberSaveable { mutableStateOf(false) }
    UiStateContent(
        uiStateDelegate = viewModel,
        modifier = Modifier,
        onEventEffect = { event ->
            when (event) {
                MovieTMDBAppViewModel.Event.LogoutSuccess -> {
                    navigator.navigateTo(
                        route = LoginRouter,
                        popUpToRoute = MovieListRouter,
                        inclusive = true
                    )
                }
            }
        },
    ) { uiState ->
        if (uiState.isShowSettingDialog) {
            SettingsDialog(
                onLogout = {
                    viewModel.logoutAccount()
                },
                onDismiss = {
                    viewModel.toggleSettingDialog(false)
                })
        }
        HandleNavigationIntents(
            navigator = navigator,
            displayToastMessage = { message ->
                context.showToast(message)
            },
            displaySnackBar = { message, actionLabel, withDismissAction ->
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    withDismissAction = withDismissAction
                )
            },
            toggleSettingDialogVisibility = viewModel::toggleSettingDialog,
            toggleForceUpdateDialog = {},
            toggleMaintenanceMode = {},
            toggleLogoutDialogVisibility = {},
        )
        if (!hasLaunched.value) {
            LaunchedEffect(Unit) {
                navigator.displaySnackBar(
                    message = context.getString(R.string.welcome_to_movie_app),
                    withDismissAction = true,
                    duration = 0,
                )
                hasLaunched.value = true
            }
        }
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {
            uiState.isLogin?.let { isLogin ->
                MovieNavHost(
                    navigator = navigator,
                    isLogin = isLogin,
                )
            }
        }
    }
}
