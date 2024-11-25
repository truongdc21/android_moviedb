package com.truongdc.android.base.data.repository.impl

import com.truongdc.android.base.common.enums.DarkThemeConfig
import com.truongdc.android.base.common.enums.ThemeBrand
import com.truongdc.android.base.data.local.datastores.AppStateDataStore
import com.truongdc.android.base.data.model.AppState
import com.truongdc.android.base.data.repository.MainRepository
import com.truongdc.android.base.resource.locale.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val appStateDataStore: AppStateDataStore
) : MainRepository {
    override val appState: Flow<AppState>
        get() = appStateDataStore.getAppState()

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        appStateDataStore.saveAppState(
            appState.first().copy(themeBrand = themeBrand)
        )
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        appStateDataStore.saveAppState(
            appState.first().copy(darkThemeConfig = darkThemeConfig)
        )
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        appStateDataStore.saveAppState(
            appState.first().copy(useDynamicColor = useDynamicColor)
        )
    }

    override suspend fun setLanguage(language: Language) {
        appStateDataStore.saveAppState(
            appState.first().copy(language = language.languageCode)
        )
    }
}
