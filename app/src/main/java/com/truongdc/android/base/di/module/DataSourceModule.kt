package com.truongdc.android.base.di.module

import com.truongdc.android.base.data.source.MovieDataSource
import com.truongdc.android.base.data.remote.MovieRemoteImpl
import com.truongdc.android.base.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun providerMovieRemoteSource(apiService: ApiService): MovieDataSource.Remote {
        return MovieRemoteImpl(apiService)
    }

}