package com.truongdc.movie_tmdb.core.network.di

import com.truongdc.movie_tmdb.core.network.MovieService
import com.truongdc.movie_tmdb.core.network.source.MovieNetworkDataSource
import com.truongdc.movie_tmdb.core.network.source.impl.MovieNetworkDataSourceImpl
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
    fun providerMovieRemoteSource(movieService: MovieService): MovieNetworkDataSource {
        return MovieNetworkDataSourceImpl(movieService)
    }

}