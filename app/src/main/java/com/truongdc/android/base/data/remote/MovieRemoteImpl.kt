package com.truongdc.android.base.data.remote

import com.truongdc.android.base.data.MovieDataSource
import com.truongdc.android.base.data.remote.api.ApiService
import com.truongdc.android.base.data.remote.reponses.BaseResponse
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.common.constant.Constants

class MovieRemoteImpl(private val apiService: ApiService) : MovieDataSource.Remote {

    override suspend fun getMovies(apiKey: String, pageNumber: Int): BaseResponse<List<Movie>> {
        return apiService.getTopRateMovies(apiKey = Constants.BASE_API_KEY, page = pageNumber)
    }

    override suspend fun getMovieDetail(movieId: Int): Movie {
        return  apiService.getMovieDetails(movieId = movieId, apiKey =  Constants.BASE_API_KEY)
    }

}