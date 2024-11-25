package com.truongdc.android.base.data.model

import com.truongdc.android.base.common.enums.DarkThemeConfig
import com.truongdc.android.base.common.enums.ThemeBrand

data class AppState(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val language: String,
)