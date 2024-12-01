package com.truongdc.movie_tmdb.core.network

import com.truongdc.movie_tmdb.core.model.BaseResponse
import com.truongdc.movie_tmdb.core.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/top_rated?")
    suspend fun fetchTopRateMovies(
        @Query("api_key") apiKey: String?,
        @Query("page") page: Int = 1
    ): BaseResponse<List<Movie>>

    @GET("movie/{movieId}")
    suspend fun fetchMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String?
    ): Movie

}