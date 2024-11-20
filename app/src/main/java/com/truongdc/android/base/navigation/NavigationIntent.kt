package com.truongdc.android.base.navigation

import android.widget.Toast
import androidx.compose.material3.SnackbarDuration

sealed class NavigationIntent {
    data class ShowToast(
        val message: String,
        val duration: Int = Toast.LENGTH_SHORT
    ) : NavigationIntent()

    data class ShowSnackBar(
        val message: String,
        val actionLabel: String? = null,
        val withDismissAction: Boolean = false,
        val duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short
            else SnackbarDuration.Indefinite
    ) : NavigationIntent()

    data class ShowSettingDialog(
        val isShowDialog: Boolean
    ) : NavigationIntent()

    data class ShowForceUpdate(
        val isShowDialog: Boolean
    ) : NavigationIntent()

    data class ShowLogoutDialog(
        val isShowDialog: Boolean
    ) : NavigationIntent()

    data class ShowMaintenanceMode(
        val isShowDialog: Boolean
    ) : NavigationIntent()
}