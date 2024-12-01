package com.truongdc.movie_tmdb.core.designsystem.dimens

import androidx.compose.runtime.compositionLocalOf

enum class Orientation {
    Portrait, Landscape;

    fun isLandscape() = this == Landscape

    fun isPortrait() = this == Portrait
}

/**
 * A composition local for [Orientation].
 */
val LocalOrientationMode = compositionLocalOf {
    Orientation.Portrait
}
