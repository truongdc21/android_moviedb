package com.truongdc.android.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.truongdc.android.base.common.enums.DarkThemeConfig
import com.truongdc.android.base.common.enums.ThemeBrand
import com.truongdc.android.base.navigation.AppNavigator
import com.truongdc.android.base.resource.locale.Language
import com.truongdc.android.base.resource.theme.MovieDbTheme
import com.truongdc.android.base.ui.screens.app.MovieDbApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator

    private var isNavigationInitialized = false

    private val viewModel: MainActivityViewModel by viewModels()

    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.onEach {
                    uiState = it
                }.collect()
            }
        }

        installSplashScreen().setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        enableEdgeToEdge()

        setContent {
            val isDarkTheme = shouldDarkTheme(uiState = uiState)
            val navHostController = rememberNavController()
            initializeNavigation(navHostController)
            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { isDarkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { isDarkTheme },
                )
                onDispose {}
            }

            MovieDbTheme(
                darkTheme = isDarkTheme,
                androidTheme = shouldUseAndroidTheme(uiState),
                disableDynamicTheming = shouldDisableDynamicTheming(uiState),
                languageCode = shouldLanguageCode(uiState)
            ) {
                MovieDbApp(appNavigator)
            }
        }
    }

    private fun initializeNavigation(
        navHostController: NavHostController,
        shouldLogNavigation: Boolean = BuildConfig.DEBUG,
    ) {
        if (isNavigationInitialized) return
        isNavigationInitialized = true
        appNavigator.apply {
            initNav(navHostController)
            if (shouldLogNavigation) {
                observeDestinationChanges()
                observerCurrentStack(lifecycleScope)
            }
        }
    }

    @Composable
    private fun shouldLanguageCode(
        uiState: MainActivityUiState,
    ): String = when (uiState) {
        MainActivityUiState.Loading -> Language.EN.languageCode
        is MainActivityUiState.Success -> uiState.appState.language
    }

    @Composable
    private fun shouldUseAndroidTheme(
        uiState: MainActivityUiState,
    ): Boolean = when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success -> when (uiState.appState.themeBrand) {
            ThemeBrand.DEFAULT -> false
            ThemeBrand.ANDROID -> true
        }
    }

    @Composable
    private fun shouldDisableDynamicTheming(
        uiState: MainActivityUiState,
    ): Boolean = when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success -> !uiState.appState.useDynamicColor
    }

    @Composable
    private fun shouldDarkTheme(
        uiState: MainActivityUiState,
    ): Boolean = when (uiState) {
        MainActivityUiState.Loading -> isSystemInDarkTheme()
        is MainActivityUiState.Success -> when (uiState.appState.darkThemeConfig) {
            DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            DarkThemeConfig.LIGHT -> false
            DarkThemeConfig.DARK -> true
        }
    }


    private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

    private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
}
