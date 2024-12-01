plugins {
    alias(libs.plugins.movieTMDB.android.library)
    alias(libs.plugins.movieTMDB.android.library.jacoco)
    alias(libs.plugins.movieTMDB.hilt)
}

android {
    namespace = "com.truongdc.movie_tmdb.core.datastore"
}

dependencies {
    api(libs.androidx.dataStore)
    api(libs.androidx.dataStore.core)
    api(libs.androidx.dataStore.preferences)
    api(projects.core.datastoreProto)
    implementation(projects.core.model)
    testImplementation(libs.kotlinx.coroutines.test)
}