/*
 * Designed and developed by 2024 truongdc21 (Dang Chi Truong)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truongdc.movie.core.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.truongdc.movie.core.network.interceptors.AuthInterceptor
import com.truongdc.movie.core.network.interceptors.HeaderInterceptor
import com.truongdc.movie.core.network.interceptors.TokenAuthenticator
import com.truongdc.movie.core.network.provider.OkHttpClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
class OkhttpClientModule {

    @Provides
    fun providerAuthOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient {
        return OkHttpClientProvider.getOkHttpClientBuilder(
            httpLoggingInterceptor = httpLoggingInterceptor,
            chuckerInterceptor = chuckerInterceptor,
            headerInterceptor = headerInterceptor,
            authInterceptor = authInterceptor,
            tokenAuthenticator = tokenAuthenticator,
        ).build()
    }

    @Provides
    fun providerChuckerInterceptor(
        @ApplicationContext context: Context,
    ): ChuckerInterceptor {
        return OkHttpClientProvider.getChuckerInterceptor(context)
    }

    @Provides
    fun providerHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return OkHttpClientProvider.getHttpLoggingInterceptor()
    }

    @Provides
    fun providerHeaderInterceptor(): HeaderInterceptor {
        return OkHttpClientProvider.getHeaderInterceptor()
    }
}
