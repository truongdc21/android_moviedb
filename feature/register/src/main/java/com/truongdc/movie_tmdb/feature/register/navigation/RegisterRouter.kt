package com.truongdc.movie_tmdb.feature.register.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.truongdc.movie_tmdb.core.navigation.AppNavigator
import com.truongdc.movie_tmdb.feature.register.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object RegisterRouter


context(NavGraphBuilder)
fun AppNavigator.navigateToRegister(
    popUpToRoute: Any? = null, isInclusive: Boolean = false,
) {
    navigateTo(
        route = RegisterRouter,
        popUpToRoute = popUpToRoute,
        inclusive = isInclusive,
    )
}

fun NavGraphBuilder.registerScreen(
    onNavigateBack: () -> Unit,
) {
    composable<RegisterRouter> {
        RegisterScreen(
            onNavigateBack = onNavigateBack
        )
    }
}
