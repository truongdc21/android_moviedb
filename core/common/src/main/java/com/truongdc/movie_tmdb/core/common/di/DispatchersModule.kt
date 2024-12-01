package com.truongdc.movie_tmdb.core.common.di


import com.truongdc.movie_tmdb.core.common.di.annotations.DefaultDispatcher
import com.truongdc.movie_tmdb.core.common.di.annotations.IoDispatcher
import com.truongdc.movie_tmdb.core.common.di.annotations.MainDispatcher
import com.truongdc.movie_tmdb.core.common.di.annotations.UnconfinedDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @DefaultDispatcher
    @Provides
    fun providerDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providerIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providerMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @UnconfinedDispatcher
    @Provides
    fun providerMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
