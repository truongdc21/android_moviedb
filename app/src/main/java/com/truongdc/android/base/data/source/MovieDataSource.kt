package com.truongdc.android.base.data.source

import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.data.remote.reponses.BaseResponse

interface MovieDataSource {
    interface Local

    interface Remote {
        suspend fun getMovies(apiKey: String, pageNumber: Int): BaseResponse<List<Movie>>

        suspend fun getMovieDetail(movieId: Int): Movie

    }
}