package com.truongdc.android.base.data.base

import com.truongdc.android.base.common.logs.LogUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class ExecuteResult(
    private val dispatcher: CoroutineDispatcher
) {
    protected suspend fun <R> withResultContext(
        context: CoroutineContext = dispatcher,
        requestBlock: suspend CoroutineScope.() -> R
    ): DataResult<R> = withContext(context) {
        return@withContext try {
            val response = requestBlock()
            DataResult.Success(response)
        } catch (e: Exception) {
            LogUtils.e(TAG, e.message.toString())
            DataResult.Error(e)
        }
    }

    companion object {
        const val TAG = "ExecuteResult"
    }
}
