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

/**
 * Provides navigation capabilities and UI-related actions for the application.
 *
 * This interface acts as a central point for managing navigation within the app,
 * including navigating between screens, displaying toasts and snackbars,
 * showing dialogs, and handling navigation results.
 *
 * It encapsulates the navigation logic and provides a consistent way to interact
 * with the navigation system from different parts of the application.
 *
 * **Key functionalities:**
 * - Navigating to different screens using routes.
 * - Displaying toasts and snackbars for user feedback.
 * - Showing dialogs for settings, force updates, maintenance mode, and logout.
 * - Handling navigation results using a shared flow.
 * - Observing the current navigation stack and destination changes.
 */
interface AppNavigator {

    /**
     * Provides access to the navigation controller,
     */
    val navController: NavHostController

    /**
     * A channel for sending navigation intents to the navigator.
     */
    val navigationChannel: Channel<NavigationIntent>

    /**
     * Initializes the navigation controller.
     */
    fun initNav(navController: NavHostController)

    /**
     * Display a toast message.
     */
    fun displayToastMessage(
        message: String,
        duration: Int = Toast.LENGTH_SHORT,
    )

    /**
     * Display a snackbar message with optional action.
     */
    fun displaySnackBar(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: Int,
    )

    /**
     * Toggle a dialog for settings.
     */
    fun toggleSettingDialog(
        isVisible: Boolean,
    )

    /**
     * Navigates to a specific route.
     */
    fun <T : Any> navigateTo(
        route: T,
        popUpToRoute: Any? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    /**
     * Navigates back to the previous screen.
     */
    fun navigateBack(
        route: Any? = null,
        inclusive: Boolean = false,
    )

    /**
     * Navigates back to the previous screen with a result.
     */
    fun <R, T : Any> navigateBackWithResult(
        key: String,
        result: R,
        route: T? = null,
        inclusive: Boolean = false,
    )

    /**
     * Observes the current navigation stack and logs it for debugging purposes.
     */
    fun observerCurrentStack(coroutineScope: CoroutineScope)

    /**
     * Observes changes in the current destination and logs them for debugging purposes.
     */
    fun observeDestinationChanges()

}

/**
 * Implementation of [AppNavigator] that handles navigation using Jetpack Navigation.
 */
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

    override fun displayToastMessage(message: String, duration: Int) {
        navigationChannel.trySend(
            NavigationIntent.DisplayToast(message, duration)
        )
    }

    override fun displaySnackBar(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: Int,
    ) {
        navigationChannel.trySend(
            NavigationIntent.DisplaySnackBar(
                message,
                actionLabel,
                withDismissAction,
            )
        )
    }

    override fun toggleSettingDialog(isVisible: Boolean) {
        navigationChannel.trySend(
            NavigationIntent.ToggleSettingsDialog(isVisible)
        )
    }

    override fun <T : Any> navigateTo(
        route: T,
        popUpToRoute: Any?,
        inclusive: Boolean,
        isSingleTop: Boolean,
    ) {
        navController.navigate(route) {
            launchSingleTop = isSingleTop
            popUpToRoute?.let { popUpToRoute ->
                popUpTo(popUpToRoute) { this.inclusive = inclusive }
            }
        }
    }

    override fun navigateBack(route: Any?, inclusive: Boolean) {
        route?.let {
            navController.popBackStack(it, inclusive)
        } ?: navController.popBackStack()
    }

    override fun <R, T : Any> navigateBackWithResult(
        key: String,
        result: R,
        route: T?,
        inclusive: Boolean,
    ) {
        navController.previousBackStackEntry?.savedStateHandle?.set(key, result)
        route?.let {
            navController.popBackStack(it, inclusive)
        } ?: navController.popBackStack()
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
        if (BuildConfig.DEBUG) {
            Log.d("[AppNavigator]", message)
        }
    }
}
