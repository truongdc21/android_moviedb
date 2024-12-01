plugins {
    alias(libs.plugins.movieTMDB.android.library)
    alias(libs.plugins.movieTMDB.android.library.compose)
    alias(libs.plugins.movieTMDB.android.library.jacoco)
}

android {
    namespace = "com.truongdc.movie_tmdb.core.state"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(libs.androidx.lifecycle.viewModelCompose)
}