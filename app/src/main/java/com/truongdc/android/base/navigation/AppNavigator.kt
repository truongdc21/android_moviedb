package com.truongdc.android.base.navigation

import android.app.Activity
import androidx.navigation.NavHostController
import com.truongdc.android.base.BuildConfig
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import timber.log.Timber
import javax.inject.Inject

interface AppNavigator {

    val activity: Activity?
    val navController: NavHostController

    fun setActivity(activity: Activity?)

    fun setNavController(navController: NavHostController)

    fun navigateBack(
        route: String? = null,
        inclusive: Boolean = false,
    )

    fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        isInclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    fun currentRoute(): String?

    fun currentArguments(): Map<String, Any?>?

    fun observerCurrentStack()

    fun observeDestinationChanges()

}

class AppNavigatorImpl @Inject constructor() : AppNavigator {

    private var _activity: Activity? = null
    private var _navController: NavHostController? = null
    override val activity: Activity?
        get() = _activity
            ?: throw IllegalStateException("Activity not has initialize in AppNavigator!")

    override val navController: NavHostController
        get() = _navController
            ?: throw IllegalStateException("NavHostController not has initialize in AppNavigator!")

    override fun setActivity(activity: Activity?) {
        _activity = activity
    }

    override fun setNavController(navController: NavHostController) {
        _navController = navController
    }

    override fun navigateBack(route: String?, inclusive: Boolean) {
        if (activity?.isFinishing == true) return
        if (route != null) {
            navController.popBackStack(route, inclusive)
        } else {
            navController.popBackStack()
        }
    }

    override fun navigateTo(
        route: String,
        popUpToRoute: String?,
        isInclusive: Boolean,
        isSingleTop: Boolean
    ) {
        if (activity?.isFinishing == true) return
        navController.navigate(route) {
            launchSingleTop = isSingleTop
            popUpToRoute?.let { popUpToRoute ->
                popUpTo(popUpToRoute) { inclusive = isInclusive }
            }
        }
    }

    override fun currentRoute(): String? {
        return navController.currentBackStackEntry?.destination?.route
    }

    override fun currentArguments(): Map<String, Any?>? {
        return navController.currentBackStackEntry?.arguments?.let { args ->
            args.keySet().associateWith { args.get(it) }
        }
    }

    override fun observerCurrentStack() {
        val routes = mutableListOf<String>()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val route = destination.route ?: "Unknown Route"
            routes.add(route)
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
        if (BuildConfig.DEBUG) {
            Timber.tag("[AppNavigator]").d(message)
        }
    }
}
