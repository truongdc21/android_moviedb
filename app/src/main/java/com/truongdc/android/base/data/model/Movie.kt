package com.truongdc.android.base.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
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
    var originalTitle: String = ""
): Parcelable