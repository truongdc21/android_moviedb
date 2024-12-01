package com.truongdc.movie_tmdb.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class DataResult<out R> {

    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val throwable: Throwable) : DataResult<Nothing>()
    object Loading : DataResult<Nothing>()

    inline fun <M> map(block: (R) -> M): DataResult<M> {
        return when (this) {
            is Success -> Success(block(data))
            is Error -> Error(throwable)
            is Loading -> Loading
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
            Loading -> "Loading"
        }
    }
}

fun <T> Flow<T>.asResult(): Flow<DataResult<T>> = map<T, DataResult<T>> { DataResult.Success(it) }
    .onStart { emit(DataResult.Loading) }
    .catch { emit(DataResult.Error(it)) }