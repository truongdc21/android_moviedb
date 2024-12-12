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
package com.truongdc.movie.core.testing.repository

import com.truongdc.movie.core.data.repository.MainRepository
import com.truongdc.movie.core.model.AppState
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import com.truongdc.movie.core.model.User
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

val emptyUser = User(
    name = "",
    email = "",
    password = "",
)

val emptyAppState = AppState(
    themeBrand = ThemeBrand.DEFAULT,
    darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    useDynamicColor = false,
    language = Language.EN,
)

class TestMainRepository : MainRepository {

    private val _user = MutableSharedFlow<User>(replay = 1, onBufferOverflow = DROP_OLDEST)

    private val currentUser get() = _user.replayCache.firstOrNull() ?: emptyUser

    private val _isLogin = MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = DROP_OLDEST)

    private val currentIsLogin get() = _isLogin.replayCache.firstOrNull() ?: false

    private val _appState = MutableSharedFlow<AppState>(replay = 1, onBufferOverflow = DROP_OLDEST)

    private val currentAppState get() = _appState.replayCache.firstOrNull() ?: emptyAppState

    override val user: Flow<User> = _user.filterNotNull()

    override val isLogin: Flow<Boolean> = _isLogin.filterNotNull()

    override val appState: Flow<AppState> = _appState.filterNotNull()

    override suspend fun saveUser(user: User) {
        _user.tryEmit(user)
    }

    override suspend fun setIsLogin(isLogin: Boolean) {
        _isLogin.tryEmit(isLogin)
    }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        _appState.tryEmit(currentAppState.copy(themeBrand = themeBrand))
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        _appState.tryEmit(currentAppState.copy(darkThemeConfig = darkThemeConfig))
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        _appState.tryEmit(currentAppState.copy(useDynamicColor = useDynamicColor))
    }

    override suspend fun setLanguage(language: Language) {
        _appState.tryEmit(currentAppState.copy(language = language))
    }
}
