package com.truongdc.android.base.data.local.datastores

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.truongdc.android.base.common.enums.DarkThemeConfig
import com.truongdc.android.base.common.enums.ThemeBrand
import com.truongdc.android.base.data.model.AppState
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
            )
        }
    }

    override suspend fun clearAll() {

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
