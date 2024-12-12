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
package com.truongdc.movie.core.data.testdoubles

import com.truongdc.movie.core.model.BaseResponse
import com.truongdc.movie.core.network.model.NetworkMovie
import com.truongdc.movie.core.network.source.MovieNetworkDataSource

class TestMovieNetworkDataSource : MovieNetworkDataSource {
    private val fakeMovies = listOf(
        NetworkMovie(id = 1, title = "Fake Movie 1", overView = "Overview for Fake Movie 1"),
        NetworkMovie(id = 2, title = "Fake Movie 2", overView = "Overview for Fake Movie 2"),
        NetworkMovie(id = 3, title = "Fake Movie 3", overView = "Overview for Fake Movie 3"),
    )

    override suspend fun fetchMovies(pageNumber: Int): BaseResponse<List<NetworkMovie>> {
        return BaseResponse(
            page = 1,
            data = fakeMovies,
            totalPage = 1,
            totalResult = "${fakeMovies.size}",
        )
    }

    override suspend fun fetchMovieDetail(movieId: Int): NetworkMovie {
        return fakeMovies.firstOrNull { it.id == movieId }
            ?: throw IllegalArgumentException("Movie with ID $movieId not found.")
    }
}
