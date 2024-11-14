package com.truongdc.android.base.common.logs

import com.truongdc.android.base.BuildConfig
import timber.log.Timber

object LogUtils {
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Timber.d(tag, message)
        }
    }

    fun e(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Timber.e(tag, msg)
        }
    }

    fun e(tag: String, msg: String, throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Timber.e(tag, msg, throwable)
        }
    }

    fun thread() {
        if (BuildConfig.DEBUG) {
            Timber.e("THREAD", "${Thread.currentThread().name} has run.")
        }
    }
}
