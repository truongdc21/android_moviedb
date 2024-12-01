plugins {
    alias(libs.plugins.movieTMDB.android.library)
    alias(libs.plugins.movieTMDB.android.library.jacoco)
    alias(libs.plugins.movieTMDB.hilt)
}

android {
    namespace = "com.truongdc.movie_tmdb.core.viewmodel"
}

dependencies {
    api(projects.core.state)
    api(libs.androidx.lifecycle.viewModelCompose)
    implementation(projects.core.navigation)
    implementation(libs.kotlinx.coroutines.core)
}