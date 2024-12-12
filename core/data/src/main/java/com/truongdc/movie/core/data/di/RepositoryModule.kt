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
package com.truongdc.movie.core.data.di

import com.truongdc.movie.core.common.di.annotations.IoDispatcher
import com.truongdc.movie.core.data.repository.MainRepository
import com.truongdc.movie.core.data.repository.MovieRepository
import com.truongdc.movie.core.data.repository.impl.MainRepositoryImpl
import com.truongdc.movie.core.data.repository.impl.MovieRepositoryImpl
import com.truongdc.movie.core.datastore.AppStateDataSource
import com.truongdc.movie.core.datastore.PreferencesDataSource
import com.truongdc.movie.core.datastore.UserDataSource
import com.truongdc.movie.core.network.source.MovieNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providerMovieRepository(
        movieNetwork: MovieNetworkDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): MovieRepository {
        return MovieRepositoryImpl(movieNetwork, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providerMainRepository(
        userDataStore: UserDataSource,
        appStateDataStore: AppStateDataSource,
        preferencesDataStore: PreferencesDataSource,
    ): MainRepository {
        return MainRepositoryImpl(
            userDataStore,
            appStateDataStore,
            preferencesDataStore,
        )
    }
}
