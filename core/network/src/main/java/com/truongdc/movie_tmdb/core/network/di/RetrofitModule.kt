package com.truongdc.movie_tmdb.core.network.di

import com.squareup.moshi.Moshi
import com.truongdc.movie_tmdb.core.network.BuildConfig
import com.truongdc.movie_tmdb.core.network.MovieService
import com.truongdc.movie_tmdb.core.network.provider.ApiServiceProvider
import com.truongdc.movie_tmdb.core.network.provider.ConverterFactoryProvider
import com.truongdc.movie_tmdb.core.network.provider.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideBaseApiUrl() = BuildConfig.BASE_API_URL

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory =
        ConverterFactoryProvider.getMoshiConverterFactory(moshi)

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseUrl, okHttpClient, converterFactory)
        .build()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        ApiServiceProvider.getApiService(retrofit)
}
