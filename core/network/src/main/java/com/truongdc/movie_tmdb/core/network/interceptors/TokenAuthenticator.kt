package com.truongdc.movie_tmdb.core.network.interceptors

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        val newToken = refreshToken()
        return response.request.newBuilder()
            .header("Authorization", newToken)
            .build()
    }

    private fun refreshToken(): String = runBlocking {
        return@runBlocking "refreshToken"
    }
}