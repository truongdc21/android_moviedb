plugins {
    alias(libs.plugins.movieTMDB.android.library)
    alias(libs.plugins.movieTMDB.android.library.compose)
    alias(libs.plugins.movieTMDB.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.truongdc.movie_tmdb.core.designsystem"
}

dependencies {
    api(libs.androidx.appcompat)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.navigationSuite)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)
    api(libs.coil.kt.compose)
    api(libs.coil.kt.compose)
    implementation(projects.core.model)
    implementation(libs.androidx.core)
    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.androidx.compose.ui.testManifest)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
}
