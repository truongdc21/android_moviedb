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

import com.truongdc.movie.core.common.constant.Constants
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MovieServiceTest : ApiAbstract<MovieService>() {

    private lateinit var service: MovieService

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        service = createService(MovieService::class.java)
    }

    @Test
    fun `fetchTopRateMovies from network test`() = runTest(testDispatcher) {
        enqueueResponse("/top_rate_movies.json")
        val response = service.fetchTopRateMovies(
            apiKey = Constants.BASE_API_KEY,
            page = 1,
        )
        // Assert
        assertEquals(response.page, 1)
        assertEquals(response.data.size, 20)
        assertEquals(response.data[0].id, 278)
        assertEquals(response.totalPage, 491)
        assertEquals(response.totalResult, "9816")
    }

    @Test
    fun `fetchMovieDetail from network test`() = runTest(testDispatcher) {
        enqueueResponse("/movie_detail.json")
        val response = service.fetchMovieDetails(
            movieId = 278,
            apiKey = Constants.BASE_API_KEY,
        )
        // Assert
        assertEquals(response.id, 278)
        assertEquals(response.title, "The Shawshank Redemption")
        assertEquals(response.voteCount, 27293)
        assertEquals(response.vote, 8.708)
        assertEquals(
            response.overView,
            "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
        )
        assertEquals(response.backDropImage, "/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg")
        assertEquals(response.originalTitle, "The Shawshank Redemption")
        assertEquals(response.urlImage, "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg")
    }
}
