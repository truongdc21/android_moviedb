package com.truongdc.movie_tmdb.core.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.truongdc.movie_tmdb.core.common.constant.Constants
import com.truongdc.movie_tmdb.core.common.di.annotations.IoDispatcher
import com.truongdc.movie_tmdb.core.common.result.ExecuteResult
import com.truongdc.movie_tmdb.core.data.paging.MoviePagingSource
import com.truongdc.movie_tmdb.core.data.repository.MovieRepository
import com.truongdc.movie_tmdb.core.network.source.MovieNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieNetWork: MovieNetworkDataSource,
    @IoDispatcher dispatcherIO: CoroutineDispatcher
) : ExecuteResult(dispatcherIO), MovieRepository {
    override suspend fun fetchMovies() = withResultContext {
        Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                MoviePagingSource(movieNetWork)
            }
        ).flow
    }

    override suspend fun fetchDetailMovies(movieId: Int) = withResultContext {
        movieNetWork.fetchMovieDetail(movieId)
    }
}