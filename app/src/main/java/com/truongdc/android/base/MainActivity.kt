package com.truongdc.android.base

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.truongdc.android.base.navigation.AppNavigator
import com.truongdc.android.base.navigation.MovieNavHost
import com.truongdc.android.base.resource.theme.MovieDbTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MovieDbTheme {
                appNavigator.apply {
                    setActivity(LocalContext.current as? Activity)
                    setNavController(navController)
                    observeDestinationChanges()
                    observerCurrentStack()
                    MovieNavHost(navController)
                }
            }
        }
    }
}
