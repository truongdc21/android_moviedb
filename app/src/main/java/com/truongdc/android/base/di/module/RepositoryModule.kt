package com.truongdc.android.base.di.module

import com.truongdc.android.base.data.local.datastores.AppStateDataStore
import com.truongdc.android.base.data.repository.MainRepository
import com.truongdc.android.base.data.repository.MovieRepository
import com.truongdc.android.base.data.repository.impl.MainRepositoryImpl
import com.truongdc.android.base.data.repository.impl.MovieRepositoryImpl
import com.truongdc.android.base.data.source.MovieDataSource
import com.truongdc.android.base.di.annotations.IoDispatcher
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
        remote: MovieDataSource.Remote,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MovieRepository {
        return MovieRepositoryImpl(remote, ioDispatcher)
    }


    @Provides
    @Singleton
    fun providerMainRepository(
        appStateDataStore: AppStateDataStore
    ): MainRepository {
        return MainRepositoryImpl(appStateDataStore)
    }
}
