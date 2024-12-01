plugins {
    alias(libs.plugins.movieTMDB.android.library)
    alias(libs.plugins.movieTMDB.android.library.compose)
    alias(libs.plugins.movieTMDB.android.library.jacoco)
}

android {
    namespace = "com.truongdc.movie_tmdb.core.ui"
}

dependencies {
    api(projects.core.viewmodel)
    api(projects.core.designsystem)
    implementation(libs.androidx.metrics)
    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}
