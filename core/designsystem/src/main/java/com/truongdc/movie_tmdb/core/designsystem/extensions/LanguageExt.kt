package com.truongdc.movie_tmdb.core.designsystem.extensions

import android.content.Context
import com.truongdc.movie_tmdb.core.designsystem.R
import com.truongdc.movie_tmdb.core.model.Language

fun Language.getDisplayName(context: Context): String {
    return when (this) {
        Language.EN -> context.getString(R.string.en)
        Language.VI -> context.getString(R.string.vi)
        Language.JA -> context.getString(R.string.ja)
    }
}