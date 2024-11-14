package com.truongdc.android.base.data.remote.api

import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.data.remote.reponses.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated?")
    suspend fun getTopRateMovies(
        @Query("api_key") apiKey: String?,
        @Query("page") page: Int = 1
    ): BaseResponse<List<Movie>>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String?
    ): Movie

}