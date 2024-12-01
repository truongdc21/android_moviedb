package com.truongdc.movie_tmdb.core.navigation.di

import com.truongdc.movie_tmdb.core.navigation.AppNavigator
import com.truongdc.movie_tmdb.core.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface AppNavigatorModule {

    @Binds
    @Singleton
    fun providerAppNavigator(appNavigator: AppNavigatorImpl): AppNavigator
}