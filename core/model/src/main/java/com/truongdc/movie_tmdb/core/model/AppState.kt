package com.truongdc.movie_tmdb.core.model

data class AppState(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val language: Language,
)