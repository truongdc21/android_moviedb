package com.truongdc.movie_tmdb.core.datastore.di

import com.truongdc.movie_tmdb.core.datastore.AppStateDataStore
import com.truongdc.movie_tmdb.core.datastore.AppStateDataStoreImpl
import com.truongdc.movie_tmdb.core.datastore.PreferencesDataStore
import com.truongdc.movie_tmdb.core.datastore.PreferencesDataStoreImpl
import com.truongdc.movie_tmdb.core.datastore.UserDataStore
import com.truongdc.movie_tmdb.core.datastore.UserDataStoreImpl
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
