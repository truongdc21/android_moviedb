package com.truongdc.movie_tmdb.core.network.di

import com.squareup.moshi.Moshi
import com.truongdc.movie_tmdb.core.network.provider.MoshiBuilderProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MoshiModule {

    @Provides
    fun provideMoshi(): Moshi = MoshiBuilderProvider.moshiBuilder.build()
}