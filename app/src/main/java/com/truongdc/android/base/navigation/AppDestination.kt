package com.truongdc.android.base.navigation

import androidx.lifecycle.SavedStateHandle
import com.truongdc.android.base.common.extensions.defaultEmpty

sealed class AppDestination(
    protected val routeName: String, vararg params: String
) {

    companion object {
        const val KEY_MOVIE_ID = "movieId"
    }

    val route: String = if (params.isEmpty()) {
        routeName
    } else {
        val builder = StringBuilder(routeName)
        params.forEach { param -> builder.append("/{${param}}") }
        builder.toString()
    }

    open class NoArgumentsDestination(name: String) : AppDestination(name) {
        operator fun invoke(): String = routeName
    }

    internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
        val builder = StringBuilder(this)

        params.forEach { param ->
            param.second
                ?.toString()
                ?.let { arg ->
                    builder.append("/$arg")
                }
        }

        return builder.toString()
    }

    data object Splash : NoArgumentsDestination("splash")

    data object Login : NoArgumentsDestination("login")

    data object Register : NoArgumentsDestination("register")

    data object MovieList : NoArgumentsDestination("movie_list")

    data object MovieDetail : AppDestination("movie_detail", KEY_MOVIE_ID) {
        operator fun invoke(movieId: String) = routeName.appendParams(
            KEY_MOVIE_ID to movieId,
        )

        fun getMovieId(stateHandle: SavedStateHandle) =
            stateHandle.get<String>(KEY_MOVIE_ID).defaultEmpty()
    }

}
