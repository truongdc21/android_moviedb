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
package com.truongdc.movie.core.datastore

import androidx.datastore.core.DataStore
import com.truongdc.movie.core.model.AppState
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface AppStateDataSource {

    suspend fun saveAppState(appState: AppState)

    fun getAppState(): Flow<AppState>

    suspend fun clearAll()
}

class AppStateDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<AppStateProto>,
) : AppStateDataSource {

    override suspend fun saveAppState(appState: AppState) {
        dataStore.updateData { userProto ->
            userProto.toBuilder().setThemeBrand(appState.themeBrand.name)
                .setDarkThemeConfig(appState.darkThemeConfig.name)
                .setUseDynamicColor(appState.useDynamicColor.toString())
                .setLanguage(appState.language.name)
                .build()
        }
    }

    override fun getAppState(): Flow<AppState> {
        return dataStore.data.map { userProto ->
            AppState(
                themeBrand = if (userProto.themeBrand.isBlank()) {
                    ThemeBrand.DEFAULT
                } else {
                    ThemeBrand.valueOf(
                        userProto.themeBrand,
                    )
                },
                darkThemeConfig = if (userProto.darkThemeConfig.isBlank()) {
                    DarkThemeConfig.FOLLOW_SYSTEM
                } else {
                    DarkThemeConfig.valueOf(
                        userProto.darkThemeConfig,
                    )
                },
                useDynamicColor = userProto.useDynamicColor.toBoolean(),
                language = if (userProto.language.isBlank()) {
                    Language.EN
                } else {
                    Language.valueOf(
                        userProto.language,
                    )
                },
            )
        }
    }

    override suspend fun clearAll() {
        dataStore.updateData { currentData ->
            currentData.toBuilder().clear().build()
        }
    }
}
