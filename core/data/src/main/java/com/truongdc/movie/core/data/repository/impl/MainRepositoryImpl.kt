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
package com.truongdc.movie.core.data.repository.impl

import com.truongdc.movie.core.data.repository.MainRepository
import com.truongdc.movie.core.datastore.AppStateDataStore
import com.truongdc.movie.core.datastore.PreferencesDataStore
import com.truongdc.movie.core.datastore.UserDataStore
import com.truongdc.movie.core.model.AppState
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import com.truongdc.movie.core.model.User
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
            appState.first().copy(themeBrand = themeBrand),
        )
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        appStateDataStore.saveAppState(
            appState.first().copy(darkThemeConfig = darkThemeConfig),
        )
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        appStateDataStore.saveAppState(
            appState.first().copy(useDynamicColor = useDynamicColor),
        )
    }

    override suspend fun setLanguage(language: Language) {
        appStateDataStore.saveAppState(
            appState.first().copy(language = language),
        )
    }
}
