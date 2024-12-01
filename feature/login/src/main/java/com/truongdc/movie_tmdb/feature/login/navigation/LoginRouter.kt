package com.truongdc.movie_tmdb.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.truongdc.movie_tmdb.core.navigation.AppNavigator
import com.truongdc.movie_tmdb.feature.login.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginRouter

context(NavGraphBuilder)
fun AppNavigator.navigateToLogin() {
    navigateTo(
        route = LoginRouter,
    )
}

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    composable<LoginRouter> {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onNavigateToRegister = onNavigateToRegister,
        )
    }
}
