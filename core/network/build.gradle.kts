plugins {
    alias(libs.plugins.movieTMDB.android.library)
    alias(libs.plugins.movieTMDB.android.library.jacoco)
    alias(libs.plugins.movieTMDB.hilt)
    alias(libs.plugins.movieTMDB.spotless)
}

android {
    namespace = "com.truongdc.movie_tmdb.core.network"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.timber)
    implementation(libs.chucker)
    implementation(libs.okhttp.logging)
}
