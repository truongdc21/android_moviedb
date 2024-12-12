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
package com.truongdc.movie.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.truongdc.movie.core.datastore.AppStateDataSource
import com.truongdc.movie.core.datastore.AppStateDataSourceImpl
import com.truongdc.movie.core.datastore.AppStateProto
import com.truongdc.movie.core.datastore.PreferencesDataSource
import com.truongdc.movie.core.datastore.PreferencesDataSourceImpl
import com.truongdc.movie.core.datastore.UserDataSource
import com.truongdc.movie.core.datastore.UserDataSourceImpl
import com.truongdc.movie.core.datastore.UserProto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreSourceModule {

    @Provides
    @Singleton
    fun providePreferencesDataSource(
        preferencesDataStore: DataStore<Preferences>,
    ): PreferencesDataSource = PreferencesDataSourceImpl(preferencesDataStore)

    @Provides
    @Singleton
    fun providerUserDataSource(
        userDataStore: DataStore<UserProto>,
    ): UserDataSource = UserDataSourceImpl(userDataStore)

    @Provides
    @Singleton
    fun providesAppStateDataSource(
        appStateDataStore: DataStore<AppStateProto>,
    ): AppStateDataSource = AppStateDataSourceImpl(appStateDataStore)
}
