package com.truongdc.movie_tmdb.feature.movie_list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.truongdc.movie_tmdb.core.model.Movie
import com.truongdc.movie_tmdb.core.navigation.AppNavigator
import com.truongdc.movie_tmdb.feature.movie_list.MovieListScreen
import kotlinx.serialization.Serializable

@Serializable
data object MovieListRouter

context(NavGraphBuilder)
fun AppNavigator.navigateToMovieList(
    popUpToRoute: Any?,
    inclusive: Boolean = false,
) {
    navigateTo(
        route = MovieListRouter,
        popUpToRoute = popUpToRoute,
        inclusive = inclusive
    )
}

fun NavGraphBuilder.movieListScreen(
    onNavigateToDetail: (Movie) -> Unit,
    onShowSettingDialog: () -> Unit,
) {
    composable<MovieListRouter> {
        MovieListScreen(
            onNavigateToDetail = onNavigateToDetail,
            onShowSettingDialog = onShowSettingDialog,
        )
    }
}
