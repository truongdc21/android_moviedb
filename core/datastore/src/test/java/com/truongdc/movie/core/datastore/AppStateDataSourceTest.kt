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
package com.truongdc.movie.core.datastore

import com.truongdc.movie.core.datastore.fakes.FakeDataStore
import com.truongdc.movie.core.model.AppState
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class AppStateDataSourceTest {

    private lateinit var appStateDataStore: AppStateDataSource

    private lateinit var fakeDataStore: FakeDataStore<AppStateProto>

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        fakeDataStore = FakeDataStore(AppStateProto.getDefaultInstance())
        appStateDataStore = AppStateDataSourceImpl(
            dataStore = fakeDataStore,
        )
    }

    @Test
    fun saveAppState_should_success_() = testScope.runTest {
        val appState = AppState(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            useDynamicColor = true,
            language = Language.EN,
        )

        appStateDataStore.saveAppState(appState)

        // Assert
        assertEquals(appState, appStateDataStore.getAppState().first())
    }

    @Test
    fun saveAppState_should_failure_() = testScope.runTest {
        val appState = AppState(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            useDynamicColor = true,
            language = Language.EN,
        )

        appStateDataStore.saveAppState(appState)

        // Assert
        assertEquals(appState, appStateDataStore.getAppState().first())
    }

    @Test
    fun clearAppState_should_failure_() = testScope.runTest {
        val appState = AppState(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            useDynamicColor = true,
            language = Language.EN,
        )

        appStateDataStore.saveAppState(appState)
        appStateDataStore.clearAll()

        // Assert
        assertNotEquals(appState, appStateDataStore.getAppState().first())
    }
}
