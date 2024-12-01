package com.truongdc.movie_tmdb.core.navigation

import android.widget.Toast

/**
 * A sealed class representing navigation
 * intents that can be sent to the navigator.
 */
sealed class NavigationIntent {

    /**
     * Represents an intent to display a toast message.
     */
    data class DisplayToast(
        val message: String,
        val duration: Int = Toast.LENGTH_SHORT,
    ) : NavigationIntent()

    /**
     * Represents an intent to display a snackbar message.
     */
    data class DisplaySnackBar(
        val message: String,
        val actionLabel: String? = null,
        val withDismissAction: Boolean = false,
    ) : NavigationIntent()

    /**
     * Represents an intent to toggle the settings dialog visibility.
     */
    data class ToggleSettingsDialog(
        val isVisible: Boolean,
    ) : NavigationIntent()

    /**
     * Represents an intent to toggle the force update dialog visibility.
     */
    data class ToggleForceUpdateDialog(
        val isVisible: Boolean,
    ) : NavigationIntent()

    /**
     * Represents an intent to toggle the logout dialog visibility.
     */
    data class ToggleLogoutDialog(
        val isVisible: Boolean,
    ) : NavigationIntent()

    /**
     * Represents an intent to toggle the maintenance mode dialog visibility.
     */
    data class ToggleMaintenanceDialog(
        val isVisible: Boolean,
    ) : NavigationIntent()
}
