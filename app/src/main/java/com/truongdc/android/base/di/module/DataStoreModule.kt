package com.truongdc.android.base.di.module

import com.truongdc.android.base.data.local.datastores.AppStateDataStore
import com.truongdc.android.base.data.local.datastores.AppStateDataStoreImpl
import com.truongdc.android.base.data.local.datastores.PreferencesDataStore
import com.truongdc.android.base.data.local.datastores.PreferencesDataStoreImpl
import com.truongdc.android.base.data.local.datastores.UserDataStore
import com.truongdc.android.base.data.local.datastores.UserDataStoreImpl
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