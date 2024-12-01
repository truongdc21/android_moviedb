package com.truongdc.movie_tmdb.app.data.remote.interceptors

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", getAuthToken())
            .build()
        return chain.proceed(request)
    }

    private fun getAuthToken() = runBlocking {
        return@runBlocking "authToken"
    }
}
