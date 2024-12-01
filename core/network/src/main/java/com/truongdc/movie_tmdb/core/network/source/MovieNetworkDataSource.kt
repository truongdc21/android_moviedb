package com.truongdc.movie_tmdb.core.network.source

import com.truongdc.movie_tmdb.core.model.BaseResponse
import com.truongdc.movie_tmdb.core.model.Movie

interface MovieNetworkDataSource {
    suspend fun fetchMovies(pageNumber: Int): BaseResponse<List<Movie>>

    suspend fun fetchMovieDetail(movieId: Int): Movie
}