package com.truongdc.android.base.ui.screens.app

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
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
import com.truongdc.android.base.R
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.navigation.AppNavigator
import com.truongdc.android.base.navigation.MovieNavHost
import com.truongdc.android.base.navigation.NavigationIntentListener
import com.truongdc.android.base.ui.screens.setting.SettingsDialog

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDbApp(
    navigator: AppNavigator,
    viewModel: MovieDbAppViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val hasLaunched = rememberSaveable { mutableStateOf(false) }
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
                    viewModel.showSettingDialog(false)
                })
        }
        NavigationIntentListener(
            navigator = navigator,
            snackbarHostState = snackbarHostState,
            showSettingDialog = viewModel::showSettingDialog
        )
        if (!hasLaunched.value) {
            LaunchedEffect(Unit) {
                navigator.showSnackBar(
                    message = context.getString(R.string.welcome_to_movie_app),
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
                hasLaunched.value = true
            }
        }
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {
            uiState.isLogin?.let { isLogin ->
                MovieNavHost(
                    navigator.navController,
                    isLogin,
                )
            }
        }
    }
}
