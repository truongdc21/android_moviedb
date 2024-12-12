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
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface PreferencesDataSource {

    val isLogIn: Flow<Boolean>

    suspend fun setIsLogIn(isLogin: Boolean)
}

@Singleton
class PreferencesDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : PreferencesDataSource {

    companion object {
        private val KEY_LOGIN = booleanPreferencesKey("is_login")
    }

    override val isLogIn: Flow<Boolean> = dataStore.data.map { preferences ->
        val isLogin = preferences[KEY_LOGIN] ?: false
        isLogin
    }

    override suspend fun setIsLogIn(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_LOGIN] = isLogin
        }
    }
}
