package com.truongdc.movie_tmdb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.truongdc.movie_tmdb.core.navigation.AppNavigator
import com.truongdc.movie_tmdb.feature.login.navigation.LoginRouter
import com.truongdc.movie_tmdb.feature.login.navigation.loginScreen
import com.truongdc.movie_tmdb.feature.movie_detail.navigation.movieDetailScreen
import com.truongdc.movie_tmdb.feature.movie_detail.navigation.navigateToMovieDetail
import com.truongdc.movie_tmdb.feature.movie_list.navigation.MovieListRouter
import com.truongdc.movie_tmdb.feature.movie_list.navigation.movieListScreen
import com.truongdc.movie_tmdb.feature.movie_list.navigation.navigateToMovieList
import com.truongdc.movie_tmdb.feature.register.navigation.navigateToRegister
import com.truongdc.movie_tmdb.feature.register.navigation.registerScreen


@Composable
fun MovieNavHost(
    navigator: AppNavigator,
    isLogin: Boolean,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = if (isLogin) MovieListRouter else LoginRouter
    ) {
        /**
         * Login screen
         */
        loginScreen(
            onLoginSuccess = {
                navigator.navigateToMovieList(
                    popUpToRoute = LoginRouter,
                    inclusive = true,
                )
            },
            onNavigateToRegister = {
                navigator.navigateToRegister()
            }
        )

        /**
         * Register screen
         */
        registerScreen(
            onNavigateBack = {
                navigator.navigateBack()
            }
        )

        /**
         * Movie list screen
         */
        movieListScreen(
            onNavigateToDetail = { movie ->
                navigator.navigateToMovieDetail(movie)
            },
            onShowSettingDialog = {
                navigator.toggleSettingDialog(true)
            }
        )

        /**
         * Movie detail screen
         */
        movieDetailScreen(
            onNavigateBack = {
                navigator.navigateBack()
            }
        )
    }
}
