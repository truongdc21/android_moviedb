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

import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RetrofitException : RuntimeException {
    private val errorType: String
    private lateinit var responses: Response<*>
    private var errorResponse: ErrorResponse? = null

    private constructor(type: String, cause: Throwable) : super(cause.message, cause) {
        errorType = type
    }

    private constructor(type: String, response: Response<*>) {
        errorType = type
        responses = response
    }

    constructor(type: String, errorResponse: ErrorResponse?) {
        errorType = type
        this.errorResponse = errorResponse
    }

    fun getErrorResponse() = errorResponse

    fun getMessageError(): String? {
        return when (errorType) {
            Type.SERVER -> {
                errorResponse?.messages
            }

            Type.NETWORK -> {
                getNetworkErrorMessage(cause)
            }

            Type.HTTP -> {
                responses.code().getHttpErrorMessage()
            }

            else -> null
        }
    }

    private fun getNetworkErrorMessage(throwable: Throwable?): String {
        if (throwable is SocketTimeoutException) {
            return throwable.message.toString()
        }

        if (throwable is UnknownHostException) {
            return throwable.message.toString()
        }

        if (throwable is IOException) {
            return throwable.message.toString()
        }

        return throwable?.message.toString()
    }

    private fun Int.getHttpErrorMessage(): String {
        if (this in HttpURLConnection.HTTP_MULT_CHOICE..HttpURLConnection.HTTP_USE_PROXY) {
            // Redirection
            return "It was transferred to a different URL. I'm sorry for causing you trouble"
        }
        if (this in HttpURLConnection.HTTP_BAD_REQUEST..HttpURLConnection.HTTP_UNSUPPORTED_TYPE) {
            // Client error
            return "An error occurred on the application side. Please try again later!"
        }
        if (this in HttpURLConnection.HTTP_INTERNAL_ERROR..HttpURLConnection.HTTP_VERSION) {
            // Server error
            return "A server error occurred. Please try again later!"
        }

        // Unofficial error
        return "An error occurred. Please try again later!"
    }

    companion object {

        fun toNetworkError(cause: Throwable): RetrofitException {
            return RetrofitException(Type.NETWORK, cause)
        }

        fun toHttpError(response: Response<*>): RetrofitException {
            return RetrofitException(Type.HTTP, response)
        }

        fun toUnexpectedError(cause: Throwable): RetrofitException {
            return RetrofitException(Type.UNEXPECTED, cause)
        }

        fun toServerError(response: ErrorResponse): RetrofitException {
            return RetrofitException(type = Type.SERVER, errorResponse = response)
        }
    }
}
