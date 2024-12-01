plugins {
    alias(libs.plugins.movieTMDB.android.feature)
    alias(libs.plugins.movieTMDB.android.library.compose)
    alias(libs.plugins.movieTMDB.android.library.jacoco)
}

android {
    namespace = "com.truongdc.movie_tmdb.feature.register"
}

dependencies {
    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}
