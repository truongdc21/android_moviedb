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
package com.truongdc.movie.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.truongdc.movie.core.model.Movie
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class NetworkMovie(
    @Json(name = "id")
    var id: Int = -1,

    @Json(name = "backdrop_path")
    var backDropImage: String = "",

    @Json(name = "overview")
    var overView: String = "",

    @Json(name = "vote_average")
    var vote: Double = 0.0,

    @Json(name = "vote_count")
    var voteCount: Int = 0,

    @Json(name = "title")
    var title: String = "",

    @Json(name = "poster_path")
    var urlImage: String = "",

    @Json(name = "original_title")
    var originalTitle: String = "",
)

fun NetworkMovie.asExternalModel(): Movie {
    return Movie(
        id = id,
        backDropImage = backDropImage,
        overView = overView,
        vote = vote,
        voteCount = voteCount,
        title = title,
        urlImage = urlImage,
        originalTitle = originalTitle,
    )
}
