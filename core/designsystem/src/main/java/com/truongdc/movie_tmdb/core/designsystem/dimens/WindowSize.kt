package com.truongdc.movie_tmdb.core.designsystem.dimens

sealed class WindowSize(val size: Int) {

    data class Small(val value: Int) : WindowSize(value)

    data class Compact(val value: Int) : WindowSize(value)

    data class Medium(val value: Int) : WindowSize(value)

    data class Large(val value: Int) : WindowSize(value)
}