package com.truongdc.movie_tmdb.core.data.repository.impl


import com.truongdc.movie_tmdb.core.data.repository.MainRepository
import com.truongdc.movie_tmdb.core.datastore.AppStateDataStore
import com.truongdc.movie_tmdb.core.datastore.PreferencesDataStore
import com.truongdc.movie_tmdb.core.datastore.UserDataStore
import com.truongdc.movie_tmdb.core.model.AppState
import com.truongdc.movie_tmdb.core.model.DarkThemeConfig
import com.truongdc.movie_tmdb.core.model.Language
import com.truongdc.movie_tmdb.core.model.ThemeBrand
import com.truongdc.movie_tmdb.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore,
    private val appStateDataStore: AppStateDataStore,
    private val preferencesDataStore: PreferencesDataStore,
) : MainRepository {

    override val user: Flow<User>
        get() = userDataStore.getUser()


    override val isLogin: Flow<Boolean>
        get() = preferencesDataStore.isLogIn


    override val appState: Flow<AppState>
        get() = appStateDataStore.getAppState()

    override suspend fun saveUser(user: User) {
        userDataStore.saveUser(user)
    }

    override suspend fun setIsLogin(isLogin: Boolean) {
        preferencesDataStore.setIsLogIn(isLogin)
    }

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
            appState.first().copy(language = language)
        )
    }
}
