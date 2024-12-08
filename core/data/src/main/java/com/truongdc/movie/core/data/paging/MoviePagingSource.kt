/*
 * Designed and developed by 2024 truongdc21 (Dang Chi Truong)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truongdc.movie.core.data.paging

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.truongdc.movie.core.model.Movie
import com.truongdc.movie.core.network.source.MovieNetworkDataSource

class MoviePagingSource(
    private val movieNetwork: MovieNetworkDataSource,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val movies = movieNetwork.fetchMovies(
                pageNumber = currentPage,
            )
            LoadResult.Page(
                data = movies.data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.data.isEmpty()) null else movies.page + 1,
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
