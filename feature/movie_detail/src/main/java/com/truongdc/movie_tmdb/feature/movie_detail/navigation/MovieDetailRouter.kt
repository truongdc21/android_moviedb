package com.truongdc.movie_tmdb.feature.movie_detail.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.truongdc.movie_tmdb.core.model.Movie
import com.truongdc.movie_tmdb.core.navigation.AppNavigator
import com.truongdc.movie_tmdb.core.navigation.parcelableType
import com.truongdc.movie_tmdb.feature.movie_detail.MovieDetailScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class MovieDetailRouter(val movie: Movie) {
    companion object {
        val typeMap = mapOf(typeOf<Movie>() to parcelableType<Movie>())
        fun getMovie(stateHandle: SavedStateHandle) = stateHandle.get<Movie>("movie")
    }
}

context(NavGraphBuilder)
fun AppNavigator.navigateToMovieDetail(movie: Movie) {
    navController.navigate(
        MovieDetailRouter(movie)
    )
}

fun NavGraphBuilder.movieDetailScreen(
    onNavigateBack: () -> Unit,
) {
    composable<MovieDetailRouter>(
        typeMap = MovieDetailRouter.typeMap
    ) {
        MovieDetailScreen(onNavigateBack = onNavigateBack)
    }
}
