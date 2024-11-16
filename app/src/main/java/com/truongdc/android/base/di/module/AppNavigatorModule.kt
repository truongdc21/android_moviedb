package com.truongdc.android.base.di.module

import com.truongdc.android.base.navigation.AppNavigator
import com.truongdc.android.base.navigation.AppNavigatorImpl
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