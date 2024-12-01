package com.truongdc.movie_tmdb.core.network.source.impl

import com.truongdc.movie_tmdb.core.common.constant.Constants
import com.truongdc.movie_tmdb.core.model.BaseResponse
import com.truongdc.movie_tmdb.core.model.Movie
import com.truongdc.movie_tmdb.core.network.MovieService
import com.truongdc.movie_tmdb.core.network.source.MovieNetworkDataSource
import javax.inject.Inject

class MovieNetworkDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieNetworkDataSource {

    override suspend fun fetchMovies(pageNumber: Int): BaseResponse<List<Movie>> {
        return movieService.fetchTopRateMovies(apiKey = Constants.BASE_API_KEY, page = pageNumber)
    }

    override suspend fun fetchMovieDetail(movieId: Int): Movie {
        return movieService.fetchMovieDetails(movieId = movieId, apiKey = Constants.BASE_API_KEY)
    }
}
