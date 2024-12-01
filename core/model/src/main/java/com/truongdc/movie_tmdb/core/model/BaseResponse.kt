package com.truongdc.movie_tmdb.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "page")  var page: Int,
    @Json(name = "total_pages")  val totalPage: Int,
    @Json(name = "total_results") val totalResult: String,
    @Json(name = "results") var data: T,
)
