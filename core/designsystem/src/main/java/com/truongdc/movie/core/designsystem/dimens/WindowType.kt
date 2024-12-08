/*
 * Designed and developed by 2024 truongdc21 (Dang Chi Truong)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truongdc.movie.core.designsystem.dimens

import androidx.compose.runtime.compositionLocalOf

enum class WindowType {
    Small,
    Compact,
    Medium,
    Large,
    ;

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
