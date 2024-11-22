package com.truongdc.android.base.resource.dimens

import androidx.compose.runtime.compositionLocalOf

enum class WindowType {
    Small, Compact, Medium, Large
}

/**
 * A composition local for [WindowType].
 */
val LocalWindowType = compositionLocalOf {
    WindowType.Small
}

fun WindowType.isSmall() = this == WindowType.Small

fun WindowType.isCompact() = this == WindowType.Compact

fun WindowType.isMedium() = this == WindowType.Medium

fun WindowType.isLarge() = this == WindowType.Large

fun WindowType.isGreaterThanMedium(): Boolean {
    return this == WindowType.Large
}

fun WindowType.isGreaterThanCompact(): Boolean {
    return this == WindowType.Medium || this == WindowType.Large
}

fun WindowType.isGreaterThanSmall(): Boolean {
    return this == WindowType.Compact || this == WindowType.Medium || this == WindowType.Large
}
