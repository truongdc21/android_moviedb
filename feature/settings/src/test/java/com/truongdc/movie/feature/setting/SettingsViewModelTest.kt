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
package com.truongdc.movie.feature.setting

import com.truongdc.movie.core.model.AppState
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import com.truongdc.movie.core.testing.repository.TestMainRepository
import com.truongdc.movie.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class SettingsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val mainRepository = TestMainRepository()

    private lateinit var viewModel: SettingsViewModel

    private val uiState: SettingsViewModel.UiState
        get() = viewModel.uiStateFlow.value

    @Before
    fun setup() {
        viewModel = SettingsViewModel(mainRepository = mainRepository)
    }

    @Test
    fun `init collects appState and updates UiState`() = runTest {
        val initialAppState = AppState(
            themeBrand = ThemeBrand.DEFAULT,
            useDynamicColor = true,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            language = Language.EN,
        )

        mainRepository.setThemeBrand(initialAppState.themeBrand)
        mainRepository.setDynamicColorPreference(initialAppState.useDynamicColor)
        mainRepository.setDarkThemeConfig(initialAppState.darkThemeConfig)
        mainRepository.setLanguage(initialAppState.language)

        assertEquals(
            UserEditableSettings(
                brand = ThemeBrand.DEFAULT,
                useDynamicColor = true,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            ),
            uiState.userEditableSettings,
        )
    }

    @Test
    fun `setThemeBrand updates themeBrand in MainRepository`() = runTest {
        val newBrand = ThemeBrand.ANDROID
        viewModel.setThemeBrand(newBrand)

        assertEquals(newBrand, mainRepository.appState.first().themeBrand)
    }

    @Test
    fun `setDarkTheme updates darkThemeConfig in MainRepository`() = runTest {
        val newDarkThemeConfig = DarkThemeConfig.DARK
        viewModel.setDarkTheme(newDarkThemeConfig)

        assertEquals(newDarkThemeConfig, mainRepository.appState.first().darkThemeConfig)
    }

    @Test
    fun `setDynamicColor updates useDynamicColor in MainRepository`() = runTest {
        val newDynamicColor = false
        viewModel.setDynamicColor(newDynamicColor)

        assertEquals(newDynamicColor, mainRepository.appState.first().useDynamicColor)
    }

    @Test
    fun `setLanguage updates language in MainRepository`() = runTest {
        val newLanguage = Language.JA
        viewModel.setLanguage(newLanguage)

        assertEquals(newLanguage, mainRepository.appState.first().language)
    }

    @Test
    fun `onSubmitSettings updates UserEditableSettings and test check updates UiState `() = runTest {
        val newSettings = UserEditableSettings(
            brand = ThemeBrand.ANDROID,
            useDynamicColor = false,
            darkThemeConfig = DarkThemeConfig.LIGHT,
        )

        // Simulate a submit action
        viewModel.setThemeBrand(newSettings.brand)
        viewModel.setDynamicColor(newSettings.useDynamicColor)
        viewModel.setDarkTheme(newSettings.darkThemeConfig)

        // Assert
        assertEquals(newSettings, uiState.userEditableSettings)
    }
}
