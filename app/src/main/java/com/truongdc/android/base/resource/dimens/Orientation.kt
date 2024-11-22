package com.truongdc.android.base.resource.dimens

import androidx.compose.runtime.compositionLocalOf

enum class Orientation {
    Portrait, Landscape
}

/**
 * A composition local for [Orientation].
 */
val LocalOrientationMode = compositionLocalOf {
    Orientation.Portrait
}

fun Orientation.isLandscape() = this == Orientation.Landscape

fun Orientation.isPortrait() = this == Orientation.Portrait