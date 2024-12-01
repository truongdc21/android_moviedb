package com.truongdc.movie_tmdb.core.datastore

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
