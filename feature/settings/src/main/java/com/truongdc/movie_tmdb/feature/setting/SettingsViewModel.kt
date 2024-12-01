package com.truongdc.movie_tmdb.feature.setting

import androidx.lifecycle.viewModelScope
import com.truongdc.movie_tmdb.core.common.utils.collectIn
import com.truongdc.movie_tmdb.core.data.repository.MainRepository
import com.truongdc.movie_tmdb.core.model.DarkThemeConfig
import com.truongdc.movie_tmdb.core.model.Language
import com.truongdc.movie_tmdb.core.model.ThemeBrand
import com.truongdc.movie_tmdb.core.state.UiStateDelegateImpl
import com.truongdc.movie_tmdb.core.viewmodel.UiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : UiStateViewModel<SettingsViewModel.UiState, SettingsViewModel.Event>(
    UiStateDelegateImpl(UiState())
) {

    data class UiState(
        val userEditableSettings: UserEditableSettings = UserEditableSettings()
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
                        darkThemeConfig = it.darkThemeConfig
                    )
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
