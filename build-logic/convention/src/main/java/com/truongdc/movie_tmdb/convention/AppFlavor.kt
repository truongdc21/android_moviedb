package com.truongdc.movie_tmdb.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class AppFlavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val appName: String,
    val baseApiUrl: String,
) {
    develop(
        dimension = FlavorDimension.contentType,
        applicationIdSuffix = ".dev",
        appName = "Movie Dev",
        baseApiUrl = "https://api.themoviedb.org/3/"
    ),
    staging(
        dimension = FlavorDimension.contentType,
        applicationIdSuffix = ".stg",
        appName = "Movie Stg",
        baseApiUrl = "https://api.themoviedb.org/3/"
    ),
    production(
        dimension = FlavorDimension.contentType,
        appName = "Movie TMDB",
        baseApiUrl = "https://api.themoviedb.org/3/"
    )
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: AppFlavor) -> Unit = {},
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.contentType.name
        productFlavors {
            AppFlavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null) {
                            applicationIdSuffix = it.applicationIdSuffix
                        }
                    }
                    resValue("string", "app_name", "\"${it.appName}\"")
                    buildConfigField("String", "BASE_API_URL", "\"${it.baseApiUrl}\"")
                }
            }
        }
    }
}
