package com.truongdc.movie_tmdb.core.navigation

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavHostController
import com.truongdc.movie_tmdb.core.common.utils.collectIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

interface AppNavigator {

    val navController: NavHostController

    val navigationChannel: Channel<NavigationIntent>

    fun initNav(navController: NavHostController)

    fun showToast(
        message: String,
        duration: Int = Toast.LENGTH_SHORT,
    )

    fun showSnackBar(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: Int,
    )

    fun showSettingDialog(
        isShowDialog: Boolean,
    )

    fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        isInclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    fun navigateBack(
        route: String? = null,
        inclusive: Boolean = false,
    )

    fun navigateBackWithResult(
        key: String,
        result: Any?,
        route: String?,
        inclusive: Boolean = false,
    )

    fun currentRoute(): String?

    fun currentArguments(): Map<String, Any?>?

    fun observerCurrentStack(coroutineScope: CoroutineScope)

    fun observeDestinationChanges()

}

class AppNavigatorImpl @Inject constructor() : AppNavigator {

    private var _navController: NavHostController? = null

    override val navController: NavHostController
        get() = _navController
            ?: throw IllegalStateException("NavHostController not has initialize in AppNavigator!")

    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override fun initNav(navController: NavHostController) {
        _navController = navController
    }

    override fun showToast(message: String, duration: Int) {
        navigationChannel.trySend(
            NavigationIntent.ShowToast(message, duration)
        )
    }

    override fun showSnackBar(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: Int,
    ) {
        navigationChannel.trySend(
            NavigationIntent.ShowSnackBar(
                message,
                actionLabel,
                withDismissAction,
            )
        )
    }

    override fun showSettingDialog(isShowDialog: Boolean) {
        navigationChannel.trySend(
            NavigationIntent.ShowSettingDialog(isShowDialog)
        )
    }

    override fun navigateTo(
        route: String,
        popUpToRoute: String?,
        isInclusive: Boolean,
        isSingleTop: Boolean,
    ) {
        navController.navigate(route) {
            launchSingleTop = isSingleTop
            popUpToRoute?.let { popUpToRoute ->
                popUpTo(popUpToRoute) { inclusive = isInclusive }
            }
        }
    }

    override fun navigateBack(
        route: String?,
        inclusive: Boolean,
    ) {
        route?.let {
            navController.popBackStack(it, inclusive)
        } ?: navController.popBackStack()
    }

    override fun navigateBackWithResult(
        key: String,
        result: Any?,
        route: String?,
        inclusive: Boolean,
    ) {
        navController.previousBackStackEntry?.savedStateHandle?.set(key, result)
        route?.let {
            navController.popBackStack(it, inclusive)
        } ?: navController.popBackStack()
    }

    override fun currentRoute(): String? {
        return navController.currentBackStackEntry?.destination?.route
    }

    override fun currentArguments(): Map<String, Any?>? {
        return navController.currentBackStackEntry?.arguments?.let { args ->
            args.keySet().associateWith { args.get(it) }
        }
    }

    @SuppressLint("RestrictedApi")
    override fun observerCurrentStack(coroutineScope: CoroutineScope) {
        val routes = mutableListOf<String>()
        navController.currentBackStack.collectIn(coroutineScope) { navBackStack ->
            routes.clear()
            navBackStack.forEach { entry -> routes.add(entry.destination.route ?: "") }
            logNavigation("Current Stack: $routes")
        }
    }

    override fun observeDestinationChanges() {
        navController.addOnDestinationChangedListener { _, destination, bundle ->
            val route = destination.route ?: "Unknown Route"
            val args = bundle?.toString() ?: "No Arguments"
            logNavigation("Navigated to [$route] with bundle [$args]")
        }
    }

    private fun logNavigation(message: String) {
        Log.d("[AppNavigator]", message)
    }
}
