package com.truongdc.movie_tmdb.core.designsystem.dimens

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val small: Dp,
    val smallMedium: Dp,
    val medium: Dp,
    val mediumLarge: Dp,
    val large: Dp,
)

val smallDimensions = Dimensions(
    small = 2.dp,
    smallMedium = 4.dp,
    medium = 6.dp,
    mediumLarge = 9.dp,
    large = 13.dp
)

val compactDimensions = Dimensions(
    small = 3.dp,
    smallMedium = 5.dp,
    medium = 8.dp,
    mediumLarge = 11.dp,
    large = 15.dp
)

val mediumDimensions = Dimensions(
    small = 5.dp,
    smallMedium = 7.dp,
    medium = 10.dp,
    mediumLarge = 13.dp,
    large = 18.dp
)

val largeDimensions = Dimensions(
    small = 8.dp,
    smallMedium = 11.dp,
    medium = 15.dp,
    mediumLarge = 20.dp,
    large = 25.dp
)

/**
 * A composition local for [Dimensions].
 */
val LocalAppDimens = compositionLocalOf {
    smallDimensions
}
