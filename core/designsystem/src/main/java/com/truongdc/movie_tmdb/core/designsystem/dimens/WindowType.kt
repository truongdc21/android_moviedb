package com.truongdc.movie_tmdb.core.designsystem.dimens

import androidx.compose.runtime.compositionLocalOf

enum class WindowType {
    Small, Compact, Medium, Large;


    fun isSmall() = this == Small

    fun isCompact() = this == Compact

    fun isMedium() = this == Medium

    fun isLarge() = this == Large

    fun isGreaterThanMedium(): Boolean {
        return this == Large
    }

    fun isGreaterThanCompact(): Boolean {
        return this == Medium || this == Large
    }

    fun isGreaterThanSmall(): Boolean {
        return this == Compact || this == Medium || this == Large
    }

}

/**
 * A composition local for [WindowType].
 */
val LocalWindowType = compositionLocalOf {
    WindowType.Small
}
