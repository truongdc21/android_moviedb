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

import com.truongdc.movie.core.datastore.AppStateDataStore
import com.truongdc.movie.core.datastore.AppStateDataStoreImpl
import com.truongdc.movie.core.datastore.PreferencesDataStore
import com.truongdc.movie.core.datastore.PreferencesDataStoreImpl
import com.truongdc.movie.core.datastore.UserDataStore
import com.truongdc.movie.core.datastore.UserDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {

    @Binds
    @Singleton
    fun providerUserDatastore(impl: UserDataStoreImpl): UserDataStore

    @Binds
    @Singleton
    fun providesPreferencesDataStore(impl: PreferencesDataStoreImpl): PreferencesDataStore

    @Binds
    @Singleton
    fun providesAppStateDataStore(impl: AppStateDataStoreImpl): AppStateDataStore
}
