package com.truongdc.android.base.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.truongdc.android.base.data.source.MovieDataSource
import com.truongdc.android.base.data.base.ExecuteResult
import com.truongdc.android.base.data.remote.reponses.MoviePagingSource
import com.truongdc.android.base.data.repository.MovieRepository
import com.truongdc.android.base.di.annotations.IoDispatcher
import com.truongdc.android.base.common.constant.Constants
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remote: MovieDataSource.Remote,
    @IoDispatcher dispatcherIO: CoroutineDispatcher
) : ExecuteResult(dispatcherIO), MovieRepository {
    override suspend fun getMovies() = withResultContext {
        Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                MoviePagingSource(remote)
            }
        ).flow
    }

    override suspend fun getDetailMovies(movieId: Int) = withResultContext {
        remote.getMovieDetail(movieId)
    }

}