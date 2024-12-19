/*
 * Designed and developed by 2024 truongdc21 (Dang Chi Truong)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truongdc.movie.feature.register.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.truongdc.movie.core.navigation.AppNavigator
import com.truongdc.movie.feature.register.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object RegisterRouter

context(NavGraphBuilder)
fun AppNavigator.navigateToRegister(
    popUpToRoute: Any? = null,
    isInclusive: Boolean = false,
) {
    navigateTo(
        route = RegisterRouter,
        popUpToRoute = popUpToRoute,
        inclusive = isInclusive,
    )
}

fun NavGraphBuilder.registerScreen(
    onNavigateBack: () -> Unit,
) {
    composable<RegisterRouter> {
        RegisterScreen(
            onNavigateBack = onNavigateBack,
        )
    }
}