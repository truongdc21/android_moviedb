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
package com.truongdc.movie.core.network

import com.truongdc.movie.core.network.model.NetworkMovie
import com.truongdc.movie.core.network.source.MovieNetworkDataSource
import com.truongdc.movie.core.network.source.impl.MovieNetworkDataSourceImpl
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MovieNetworkDataSourceTest : ApiAbstract<MovieService>() {

    private lateinit var service: MovieService

    private lateinit var source: MovieNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        service = createService(MovieService::class.java)
        source = MovieNetworkDataSourceImpl(
            movieService = service,
        )
    }

    @Test
    fun `fetchMovies network source test`() = runTest(testDispatcher) {
        enqueueResponse("/top_rate_movies.json")
        val response = source.fetchMovies(
            pageNumber = 1,
        )
        // Assert
        assertEquals(response.page, 1)
        assertEquals(response.data.size, 20)
        assertEquals(response.data[0].id, 278)
        assertEquals(response.totalPage, 491)
        assertEquals(response.totalResult, "9816")
    }

    @Test
    fun `fetchMovieDetail network source test`() = runTest(testDispatcher) {
        enqueueResponse("/movie_detail.json")
        val actualResponse = source.fetchMovieDetail(movieId = 278)
        // Assert
        assertEquals(
            NetworkMovie(
                id = 278,
                title = "The Shawshank Redemption",
                voteCount = 27293,
                vote = 8.708,
                overView = "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                backDropImage = "/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg",
                originalTitle = "The Shawshank Redemption",
                urlImage = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
            ),
            actualResponse,
        )
    }
}
