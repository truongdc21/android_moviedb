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
package com.truongdc.movie.core.common.extensions

import java.util.Date

fun String?.defaultEmpty(): String {
    return this.default("")
}

fun Int?.defaultZero(): Int {
    return this.default(0)
}

fun Long?.defaultZero(): Long {
    return this.default(0)
}

fun Double?.defaultZero(): Double {
    return this.default(0.0)
}

fun Float?.defaultZero(): Float {
    return this.default(0.0f)
}

fun <T> List<T>?.defaultEmpty(): List<T> {
    return this ?: listOf()
}

fun Date?.defaultToday(): Date {
    return this.default(Date())
}

fun Boolean?.defaultFalse(): Boolean {
    return this.default(false)
}

fun Boolean?.defaultTrue(): Boolean {
    return this.default(true)
}

fun <T> T?.default(default: T): T {
    return this ?: default
}
