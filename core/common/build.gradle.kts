plugins {
    alias(libs.plugins.movieTMDB.jvm.library)
    alias(libs.plugins.movieTMDB.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}