package com.truongdc.movie_tmdb.core.common.result


import com.truongdc.movie_tmdb.core.common.di.annotations.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class ExecuteResult(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    protected suspend fun <R> withResultContext(
        context: CoroutineContext = dispatcher,
        requestBlock: suspend CoroutineScope.() -> R
    ): DataResult<R> = withContext(context) {
        return@withContext try {
            val response = requestBlock()
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    companion object {
        const val TAG = "ExecuteResult"
    }
}
