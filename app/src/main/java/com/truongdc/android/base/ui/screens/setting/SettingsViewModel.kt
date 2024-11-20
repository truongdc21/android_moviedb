package com.truongdc.android.base.ui.screens.setting

import androidx.lifecycle.viewModelScope
import com.truongdc.android.base.base.UiStateViewModel
import com.truongdc.android.base.base.state.UiStateDelegateImpl
import com.truongdc.android.base.common.enums.DarkThemeConfig
import com.truongdc.android.base.common.enums.ThemeBrand
import com.truongdc.android.base.common.utils.collectIn
import com.truongdc.android.base.data.repository.MainRepository
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
}

data class UserEditableSettings(
    val brand: ThemeBrand = ThemeBrand.DEFAULT,
    val useDynamicColor: Boolean = true,
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
)
