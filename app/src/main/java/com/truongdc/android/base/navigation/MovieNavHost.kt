package com.truongdc.android.base.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.truongdc.android.base.ui.screens.login.LoginScreen
import com.truongdc.android.base.ui.screens.movie_detail.compose.MovieDetailScreen
import com.truongdc.android.base.ui.screens.movies.MovieListScreen
import com.truongdc.android.base.ui.screens.register.RegisterScreen

@Composable
fun MovieNavHost(
    navController: NavHostController,
    isLogin: Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLogin) AppDestination.MovieList() else AppDestination.Login()
    ) {
        composable(AppDestination.Login) { LoginScreen() }
        composable(AppDestination.Register) { RegisterScreen() }
        composable(AppDestination.MovieList) { MovieListScreen() }
        composable(AppDestination.MovieDetail) { MovieDetailScreen() }
    }
}

fun NavGraphBuilder.composable(
    destination: AppDestination,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content,
    )
}
