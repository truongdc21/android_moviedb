
plugins {
    alias(libs.plugins.movieTMDB.android.library)
    alias(libs.plugins.movieTMDB.android.library.jacoco)
    alias(libs.plugins.movieTMDB.hilt)
}

android {
    namespace = "com.truongdc.movie_tmdb.core.data"
}

dependencies {
    api(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.core.network)
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
}
