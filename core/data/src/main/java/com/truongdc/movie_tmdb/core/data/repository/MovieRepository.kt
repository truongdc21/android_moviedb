package com.truongdc.movie_tmdb.core.data.repository

import androidx.paging.PagingData
import com.truongdc.movie_tmdb.core.common.result.DataResult
import com.truongdc.movie_tmdb.core.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchMovies(): DataResult<Flow<PagingData<Movie>>>

    suspend fun fetchDetailMovies(movieId: Int): DataResult<Movie>
}