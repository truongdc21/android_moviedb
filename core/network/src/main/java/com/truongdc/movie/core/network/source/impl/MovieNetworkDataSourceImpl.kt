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
package com.truongdc.movie.core.network.source.impl

import com.truongdc.movie.core.common.constant.Constants
import com.truongdc.movie.core.model.BaseResponse
import com.truongdc.movie.core.model.Movie
import com.truongdc.movie.core.network.MovieService
import com.truongdc.movie.core.network.source.MovieNetworkDataSource
import javax.inject.Inject

class MovieNetworkDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
) : MovieNetworkDataSource {

    override suspend fun fetchMovies(pageNumber: Int): BaseResponse<List<Movie>> {
        return movieService.fetchTopRateMovies(apiKey = Constants.BASE_API_KEY, page = pageNumber)
    }

    override suspend fun fetchMovieDetail(movieId: Int): Movie {
        return movieService.fetchMovieDetails(movieId = movieId, apiKey = Constants.BASE_API_KEY)
    }
}
