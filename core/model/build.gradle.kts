plugins {
    alias(libs.plugins.movieTMDB.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.truongdc.movie_tmdb.core.model"
}

dependencies {
    api(libs.moshi)
    api(libs.moshi.kotlin)
    api(libs.moshi.adapter)
    api(libs.kotlinx.serialization.json)
}