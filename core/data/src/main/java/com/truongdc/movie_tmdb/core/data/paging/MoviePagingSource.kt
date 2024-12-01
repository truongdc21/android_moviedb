package com.truongdc.movie_tmdb.core.data.paging

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.truongdc.movie_tmdb.core.model.Movie
import com.truongdc.movie_tmdb.core.network.source.MovieNetworkDataSource

class MoviePagingSource(
    private val movieNetwork: MovieNetworkDataSource
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val movies = movieNetwork.fetchMovies(
                pageNumber = currentPage
            )
            LoadResult.Page(
                data = movies.data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.data.isEmpty()) null else movies.page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}