plugins {
    alias(libs.plugins.movieTMDB.android.library)
    alias(libs.plugins.movieTMDB.android.library.compose)
    alias(libs.plugins.movieTMDB.android.library.jacoco)
    alias(libs.plugins.movieTMDB.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.truongdc.movie_tmdb.core.navigation"
}

dependencies {
    api(libs.androidx.navigation.compose)
    api(libs.androidx.navigation.testing)
    api(libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}