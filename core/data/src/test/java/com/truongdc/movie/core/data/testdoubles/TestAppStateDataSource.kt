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
package com.truongdc.movie.core.data.testdoubles

import com.truongdc.movie.core.datastore.AppStateDataSource
import com.truongdc.movie.core.model.AppState
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestAppStateDataSource : AppStateDataSource {

    private val appStateFlow = MutableStateFlow(
        AppState(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            useDynamicColor = false,
            language = Language.EN,
        ),
    )

    override suspend fun saveAppState(appState: AppState) {
        // Update the in-memory state
        appStateFlow.value = appState
    }

    override fun getAppState(): Flow<AppState> {
        // Return the current state as a flow
        return appStateFlow
    }

    override suspend fun clearAll() {
        // Reset the state to default
        appStateFlow.value = AppState(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            useDynamicColor = false,
            language = Language.EN,
        )
    }
}
