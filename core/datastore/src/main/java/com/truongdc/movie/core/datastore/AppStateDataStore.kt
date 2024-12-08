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
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.truongdc.movie.core.model.AppState
import com.truongdc.movie.core.model.DarkThemeConfig
import com.truongdc.movie.core.model.Language
import com.truongdc.movie.core.model.ThemeBrand
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

interface AppStateDataStore {

    suspend fun saveAppState(appState: AppState)

    fun getAppState(): Flow<AppState>

    suspend fun clearAll()
}

class AppStateDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppStateDataStore {

    companion object {
        private const val FILENAME = "app_state_proto_data_store_pb"
    }

    private val Context.dataStore: DataStore<AppStateProto> by dataStore(
        fileName = FILENAME,
        serializer = AppStateSerialize,
    )

    override suspend fun saveAppState(appState: AppState) {
        context.dataStore.updateData { userProto ->
            userProto.toBuilder().setThemeBrand(appState.themeBrand.name)
                .setDarkThemeConfig(appState.darkThemeConfig.name)
                .setUseDynamicColor(appState.useDynamicColor.toString())
                .setLanguage(appState.language.name)
                .build()
        }
    }

    override fun getAppState(): Flow<AppState> {
        return context.dataStore.data.map { userProto ->
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
        context.dataStore.updateData { currentData ->
            currentData.toBuilder().clear().build()
        }
    }

    private object AppStateSerialize : Serializer<AppStateProto> {
        override val defaultValue: AppStateProto = AppStateProto.getDefaultInstance()
        override suspend fun readFrom(input: InputStream): AppStateProto {
            try {
                return AppStateProto.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override suspend fun writeTo(t: AppStateProto, output: OutputStream) = t.writeTo(output)
    }
}
