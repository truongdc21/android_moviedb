package com.truongdc.movie_tmdb.core.data.di

import com.truongdc.movie_tmdb.core.common.di.annotations.IoDispatcher
import com.truongdc.movie_tmdb.core.data.repository.MainRepository
import com.truongdc.movie_tmdb.core.data.repository.MovieRepository
import com.truongdc.movie_tmdb.core.data.repository.impl.MainRepositoryImpl
import com.truongdc.movie_tmdb.core.data.repository.impl.MovieRepositoryImpl
import com.truongdc.movie_tmdb.core.datastore.AppStateDataStore
import com.truongdc.movie_tmdb.core.datastore.PreferencesDataStore
import com.truongdc.movie_tmdb.core.datastore.UserDataStore
import com.truongdc.movie_tmdb.core.network.source.MovieNetworkDataSource
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
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MovieRepository {
        return MovieRepositoryImpl(movieNetwork, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providerMainRepository(
        userDataStore: UserDataStore,
        appStateDataStore: AppStateDataStore,
        preferencesDataStore: PreferencesDataStore,
    ): MainRepository {
        return MainRepositoryImpl(
            userDataStore,
            appStateDataStore,
            preferencesDataStore,
        )
    }
}
