package com.truongdc.android.base.data.remote.reponses

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.common.constant.Constants
import com.truongdc.android.base.data.source.MovieDataSource
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val movieRemoteSource : MovieDataSource.Remote
): PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val movies = movieRemoteSource.getMovies(
                apiKey = Constants.BASE_API_KEY,
                pageNumber = currentPage
            )
            LoadResult.Page(
                data = movies.data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.data.isEmpty()) null else movies.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}