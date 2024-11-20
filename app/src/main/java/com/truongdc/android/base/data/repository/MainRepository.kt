package com.truongdc.android.base.data.repository

import com.truongdc.android.base.common.enums.DarkThemeConfig
import com.truongdc.android.base.common.enums.ThemeBrand
import com.truongdc.android.base.data.model.AppState
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    val appState: Flow<AppState>

    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
}
