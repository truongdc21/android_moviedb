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
package com.truongdc.movie.core.data.model

import com.truongdc.movie.core.network.model.NetworkMovie
import com.truongdc.movie.core.network.model.asExternalModel
import org.junit.Test
import kotlin.test.assertEquals

class NetworkMovieMapToExternal {
    @Test
    fun networkMovieMapToExternal() {
        val networkMovie = NetworkMovie(
            id = 278,
            title = "The Shawshank Redemption",
            voteCount = 27293,
            vote = 8.708,
            overView = "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
            backDropImage = "/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg",
            originalTitle = "The Shawshank Redemption",
            urlImage = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
        )
        val external = networkMovie.asExternalModel()

        // Assert
        assertEquals(278, external.id)
        assertEquals("The Shawshank Redemption", external.title)
        assertEquals(27293, external.voteCount)
        assertEquals(8.708, external.vote)
        assertEquals(
            "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
            external.overView,
        )
        assertEquals("/zfbjgQE1uSd9wiPTX4VzsLi0rGG.jpg", external.backDropImage)
        assertEquals("The Shawshank Redemption", external.originalTitle)
        assertEquals("/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg", external.urlImage)
    }
}
