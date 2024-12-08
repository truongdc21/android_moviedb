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

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface PreferencesDataStore {

    val isLogIn: Flow<Boolean>

    suspend fun setIsLogIn(isLogin: Boolean)
}

@Singleton
class PreferencesDataStoreImpl @Inject constructor(@ApplicationContext private val context: Context) :
    PreferencesDataStore {
    companion object {
        private const val PREFERENCES_NAME = "preferences_data_store_pb"

        private val KEY_LOGIN = booleanPreferencesKey("is_login")
    }

    private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

    override val isLogIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        val isLogin = preferences[KEY_LOGIN] ?: false
        isLogin
    }

    override suspend fun setIsLogIn(isLogin: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LOGIN] = isLogin
        }
    }
}
