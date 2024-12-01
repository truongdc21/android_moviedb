package com.truongdc.movie_tmdb.core.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.truongdc.movie_tmdb.core.model.AppState
import com.truongdc.movie_tmdb.core.model.DarkThemeConfig
import com.truongdc.movie_tmdb.core.model.Language
import com.truongdc.movie_tmdb.core.model.ThemeBrand
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
                themeBrand = if (userProto.themeBrand.isBlank()) ThemeBrand.DEFAULT else ThemeBrand.valueOf(
                    userProto.themeBrand
                ),
                darkThemeConfig = if (userProto.darkThemeConfig.isBlank()) DarkThemeConfig.FOLLOW_SYSTEM else DarkThemeConfig.valueOf(
                    userProto.darkThemeConfig
                ),
                useDynamicColor = userProto.useDynamicColor.toBoolean(),
                language = if (userProto.language.isBlank()) Language.EN else Language.valueOf(
                    userProto.language
                )
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
