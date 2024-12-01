package com.truongdc.movie_tmdb.core.network.provider

import com.truongdc.movie_tmdb.core.network.MovieService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }
}
