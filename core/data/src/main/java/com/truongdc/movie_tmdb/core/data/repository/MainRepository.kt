package com.truongdc.movie_tmdb.core.data.repository

import com.truongdc.movie_tmdb.core.model.AppState
import com.truongdc.movie_tmdb.core.model.DarkThemeConfig
import com.truongdc.movie_tmdb.core.model.Language
import com.truongdc.movie_tmdb.core.model.ThemeBrand
import com.truongdc.movie_tmdb.core.model.User
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    val user: Flow<User>

    val isLogin: Flow<Boolean>

    val appState: Flow<AppState>

    suspend fun saveUser(user: User)

    suspend fun setIsLogin(isLogin: Boolean)

    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setLanguage(language: Language)
}
