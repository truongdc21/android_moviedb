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
package com.truongdc.movie.core.data

import com.truongdc.movie.core.data.repository.MainRepository
import com.truongdc.movie.core.data.repository.impl.MainRepositoryImpl
import com.truongdc.movie.core.data.testdoubles.TestAppStateDataSource
import com.truongdc.movie.core.data.testdoubles.TestPreferencesDataSource
import com.truongdc.movie.core.data.testdoubles.TestUserDataSource
import com.truongdc.movie.core.model.AppState
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import com.truongdc.movie.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class MainRepositoryTest {

    private lateinit var repository: MainRepository
    private lateinit var userDataSource: TestUserDataSource
    private lateinit var appStateDataSource: TestAppStateDataSource
    private lateinit var preferencesDataSource: TestPreferencesDataSource

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val appState: Flow<AppState>
        get() = repository.appState

    @Before
    fun setup() {
        userDataSource = TestUserDataSource()
        appStateDataSource = TestAppStateDataSource()
        preferencesDataSource = TestPreferencesDataSource()
        repository = MainRepositoryImpl(
            userDataStore = userDataSource,
            appStateDataStore = appStateDataSource,
            preferencesDataStore = preferencesDataSource,
        )
    }

    @Test
    fun saveUser_should_success_() = testScope.runTest {
        // Arrange
        val user = User(name = "John", email = "john@example.com", password = "password123")
        repository.saveUser(user)

        // Act
        val result = repository.user.first()

        // Assert
        assertEquals(user, result)
    }

    @Test
    fun isLogin_should_return_correct_value_() = testScope.runTest {
        // Arrange
        val isLogin = true
        preferencesDataSource.setIsLogIn(isLogin)

        // Act
        val result = repository.isLogin.first()

        // Assert
        assertEquals(isLogin, result)
    }

    @Test
    fun setIsLogin_should_update_correctly_() = testScope.runTest {
        // Arrange
        val isLogin = true

        // Act
        repository.setIsLogin(isLogin)

        // Assert
        assertEquals(isLogin, preferencesDataSource.isLogIn.first())
    }

    @Test
    fun setThemeBrand_should_update_correctly_() = testScope.runTest {
        // Arrange
        val themeBrand = ThemeBrand.ANDROID
        appStateDataSource.saveAppState(appState.first().copy(themeBrand = ThemeBrand.DEFAULT))

        // Act
        repository.setThemeBrand(themeBrand)

        // Assert
        assertEquals(themeBrand, appStateDataSource.getAppState().first().themeBrand)
    }

    @Test
    fun setDarkThemeConfig_should_update_correctly_() = testScope.runTest {
        // Arrange
        val darkThemeConfig = DarkThemeConfig.DARK
        appStateDataSource.saveAppState(
            appState.first().copy(darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM),
        )

        // Act
        repository.setDarkThemeConfig(darkThemeConfig)

        // Assert
        assertEquals(darkThemeConfig, appStateDataSource.getAppState().first().darkThemeConfig)
    }

    @Test
    fun setDynamicColorPreference_should_update_correctly_() = testScope.runTest {
        // Arrange
        val useDynamicColor = true
        appStateDataSource.saveAppState(appState.first().copy(useDynamicColor = false))

        // Act
        repository.setDynamicColorPreference(useDynamicColor)

        // Assert
        assertEquals(useDynamicColor, appState.first().useDynamicColor)
    }

    @Test
    fun setDynamicColorPreference_should_update_useDynamicColor_to_false() = testScope.runTest {
        // Arrange
        val initialState = AppState(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            useDynamicColor = true,
            language = Language.EN,
        )
        appStateDataSource.saveAppState(initialState)

        // Act
        repository.setDynamicColorPreference(false)

        // Assert
        val result = appStateDataSource.getAppState().first()
        assertFalse(result.useDynamicColor)
    }

    @Test
    fun setLanguage_should_update_correctly_() = testScope.runTest {
        // Arrange
        val language = Language.JA
        appStateDataSource.saveAppState(appState.first().copy(language = Language.EN))

        // Act
        repository.setLanguage(language)

        // Assert
        assertEquals(language, appStateDataSource.getAppState().first().language)
    }
}
