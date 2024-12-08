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

import androidx.lifecycle.viewModelScope
import com.truongdc.movie.core.common.utils.collectIn
import com.truongdc.movie.core.data.repository.MainRepository
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import com.truongdc.movie.core.state.UiStateDelegateImpl
import com.truongdc.movie.core.viewmodel.UiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : UiStateViewModel<SettingsViewModel.UiState, SettingsViewModel.Event>(
    UiStateDelegateImpl(UiState()),
) {

    data class UiState(
        val userEditableSettings: UserEditableSettings = UserEditableSettings(),
    )

    sealed interface Event

    init {
        mainRepository.appState.collectIn(viewModelScope) {
            updateUiState { state ->
                state.copy(
                    userEditableSettings =
                    UserEditableSettings(
                        brand = it.themeBrand,
                        useDynamicColor = it.useDynamicColor,
                        darkThemeConfig = it.darkThemeConfig,
                    ),
                )
            }
        }
    }

    fun setThemeBrand(brand: ThemeBrand) {
        viewModelScope.launch { mainRepository.setThemeBrand(brand) }
    }

    fun setDarkTheme(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            mainRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun setDynamicColor(useDynamicColor: Boolean) {
        viewModelScope.launch {
            mainRepository.setDynamicColorPreference(useDynamicColor)
        }
    }

    fun setLanguage(language: Language) {
        viewModelScope.launch {
            mainRepository.setLanguage(language)
        }
    }
}

data class UserEditableSettings(
    val brand: ThemeBrand = ThemeBrand.DEFAULT,
    val useDynamicColor: Boolean = true,
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
)
