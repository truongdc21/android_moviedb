package com.truongdc.android.base.data.repository

import androidx.paging.PagingData
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.data.base.DataResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): DataResult<Flow<PagingData<Movie>>>

    suspend fun getDetailMovies(movieId: Int): DataResult<Movie>
}