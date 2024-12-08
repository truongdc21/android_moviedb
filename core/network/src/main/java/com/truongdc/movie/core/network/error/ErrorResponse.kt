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
package com.truongdc.movie.core.network.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.truongdc.movie.core.network.provider.MoshiBuilderProvider
import retrofit2.HttpException
import java.io.IOException
import java.text.ParseException

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "status")
    val status: Int,
    @Json(name = "messages")
    val messages: String?,
) {
    companion object {
        private const val TAG = "ErrorResponse"
        fun convertToRetrofitException(throwable: Throwable): RetrofitException {
            if (throwable is RetrofitException) {
                return throwable
            }

            // A network error happened
            if (throwable is IOException) {
                return RetrofitException.toNetworkError(throwable)
            }

            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response() ?: return RetrofitException.toUnexpectedError(
                    throwable,
                )

                response.errorBody()?.let {
                    return try {
                        val moshi = MoshiBuilderProvider.moshiBuilder.build()
                        val adapter = moshi.adapter(ErrorResponse::class.java)
                        val errorResponse = adapter.fromJson(it.string())
                        if (errorResponse != null && !errorResponse.messages.isNullOrBlank()) {
                            RetrofitException.toServerError(errorResponse)
                        } else {
                            RetrofitException.toHttpError(response)
                        }
                    } catch (e: IOException) {
                        // LogUtils.e(TAG, e.message.toString())
                        RetrofitException.toUnexpectedError(throwable)
                    } catch (e: ParseException) {
                        // LogUtils.e(TAG, e.message.toString())
                        RetrofitException.toUnexpectedError(throwable)
                    } catch (e: com.squareup.moshi.JsonDataException) {
                        // LogUtils.e(TAG, e.message.toString())
                        RetrofitException.toUnexpectedError(throwable)
                    }
                }

                return RetrofitException.toHttpError(response)
            }

            // We don't know what happened. We need to simply convert to an unknown error
            return RetrofitException.toUnexpectedError(throwable)
        }
    }
}
