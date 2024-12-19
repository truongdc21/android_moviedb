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
package com.truongdc.movie

import com.truongdc.movie.core.model.AppState
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import com.truongdc.movie.core.testing.repository.TestMainRepository
import com.truongdc.movie.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Unit tests for the [MainViewModel]
 */
class MainViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: TestMainRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repository = TestMainRepository()
        viewModel = MainViewModel(mainRepository = repository)
    }

    @Test
    fun `uiState emits Loading and then Success when appState is updated`() = runTest {
        // Initial state: Loading
        assert(viewModel.uiState.value is MainActivityUiState.Loading)

        // Set new appState
        val initialAppState = AppState(
            themeBrand = ThemeBrand.DEFAULT,
            useDynamicColor = true,
            darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
            language = Language.EN,
        )
        repository.setThemeBrand(initialAppState.themeBrand)
        repository.setDynamicColorPreference(initialAppState.useDynamicColor)
        repository.setDarkThemeConfig(initialAppState.darkThemeConfig)
        repository.setLanguage(initialAppState.language)

        assert(viewModel.uiState.first() is MainActivityUiState.Success)
        assertEquals(
            initialAppState,
            (viewModel.uiState.first() as MainActivityUiState.Success).appState,
        )
    }
}
