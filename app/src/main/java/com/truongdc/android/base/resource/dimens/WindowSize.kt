package com.truongdc.android.base.resource.dimens

sealed class WindowSize(val size: Int) {

    data class Small(val value: Int) : WindowSize(value)

    data class Compact(val value: Int) : WindowSize(value)

    data class Medium(val value: Int) : WindowSize(value)

    data class Large(val value: Int) : WindowSize(value)
}