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
package com.truongdc.movie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.truongdc.movie.core.navigation.AppNavigator
import com.truongdc.movie.feature.login.navigation.LoginRouter
import com.truongdc.movie.feature.login.navigation.loginScreen
import com.truongdc.movie.feature.movieDetail.navigation.movieDetailScreen
import com.truongdc.movie.feature.movieDetail.navigation.navigateToMovieDetail
import com.truongdc.movie.feature.movieList.navigation.MovieListRouter
import com.truongdc.movie.feature.movieList.navigation.movieListScreen
import com.truongdc.movie.feature.movieList.navigation.navigateToMovieList
import com.truongdc.movie.feature.register.navigation.navigateToRegister
import com.truongdc.movie.feature.register.navigation.registerScreen

@Composable
fun MovieNavHost(
    navigator: AppNavigator,
    isLogin: Boolean,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = if (isLogin) MovieListRouter else LoginRouter,
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
            },
        )

        /**
         * Register screen
         */
        registerScreen(
            onNavigateBack = {
                navigator.navigateBack()
            },
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
            },
        )

        /**
         * Movie detail screen
         */
        movieDetailScreen(
            onNavigateBack = {
                navigator.navigateBack()
            },
        )
    }
}
