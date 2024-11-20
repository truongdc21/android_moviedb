package com.truongdc.android.base.ui.screens.slpash

import androidx.lifecycle.ViewModel
import com.truongdc.android.base.data.local.datastores.PreferencesDataStore
import com.truongdc.android.base.navigation.AppDestination
import com.truongdc.android.base.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
    private val appNavigator: AppNavigator
) : ViewModel() {

    fun getIsLogin() = preferencesDataStore.isLogIn

    fun navigateMovies() {
        appNavigator.navigateTo(
            route = AppDestination.MovieList(),
            popUpToRoute = AppDestination.Splash.route,
            isInclusive = true
        )
    }

    fun navigateLogin() {
        appNavigator.navigateTo(
            AppDestination.Login(),
            popUpToRoute = AppDestination.Splash.route,
            isInclusive = true
        )
    }

}
