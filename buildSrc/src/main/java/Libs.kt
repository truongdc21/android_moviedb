object Libs {

    object AndroidX {
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val LIFECYCLE_RUNTIME_KTX =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME_KTX}"
        const val LIFECYCLE_RUNTIME_COMPOSE =
            "androidx.lifecycle:lifecycle-runtime-compose:${Versions.LIFECYCLE_RUNTIME_KTX}"
        const val ACTIVITY_COMPOSE =
            "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
        const val SECURITY_CRYPTO = "androidx.security:security-crypto:${Versions.SECURITY_CRYPTO}"

        const val COMPOSE_BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
        const val COMPOSE_UI = "androidx.compose.ui:ui"
        const val COMPOSE_UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
        const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
        const val COMPOSE_UI_TOOLING_PREVIEW_DEBUG = "androidx.compose.ui:ui-tooling:${Versions.UI_TOOL_PREVIEW}"
        const val COMPOSE_UI_TEST_JUNIT4 = "androidx.compose.ui:ui-test-junit4"
        const val COMPOSE_UI_TEST_JUNIT5 =
            "de.mannodermaus.junit5:android-test-compose:${Versions.COMPOSE_JUNIT5}"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling"
        const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material"
        const val NAV_COMPOSE = "androidx.navigation:navigation-compose:${Versions.NAV_COMPOSE}"

        const val CORE_TESTING = "androidx.arch.core:core-testing:${Versions.CORE_TESTING}"
        const val TEST_JUNIT = "org.junit.jupiter:junit-jupiter-api:${Versions.TEST_JUNIT}"
        const val TEST_ESPRESSO_CORE =
            "androidx.test.espresso:espresso-core:${Versions.TEST_ESPRESSO_CORE}"
        const val TEST_CORE_KTX = "androidx.test:core-ktx:${Versions.TEST_CORE_KTX}"
    }

    object Kotlin {
        const val COROUTINES_CORE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
        const val COROUTINES_ANDROID =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
        const val COROUTINES_TEST =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
    }

    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val JAVAX_INJECT = "javax.inject:javax.inject:${Versions.JAVAX_INJECT}"
    const val CHUCKER = "com.github.chuckerteam.chucker:library:${Versions.CHUCKER}"
    const val LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.LOGGING_INTERCEPTOR}"
    const val SPLASH_SCREEN = "androidx.core:core-splashscreen:${Versions.SPLASH_SCREEN}"

    object Hilt {
        const val ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"
        const val NAV_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAV_COMPOSE}"
    }

    object Moshi {
        const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI}"
        const val MOSHI_ADAPTER = "com.squareup.moshi:moshi-adapters:${Versions.MOSHI}"
        const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
    }

    object Retrofit {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val RETROFIT_MOSHI = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"
    }

    object Accompanist {
        const val INSETS = "com.google.accompanist:accompanist-insets:${Versions.ACCOMPANIST}"
        const val PAGER = "com.google.accompanist:accompanist-pager:${Versions.ACCOMPANIST}"
        const val SWIPE_REFRESH =
            "com.google.accompanist:accompanist-swiperefresh:${Versions.ACCOMPANIST}"
        const val SYSTEM_UI =
            "com.google.accompanist:accompanist-systemuicontroller:${Versions.ACCOMPANIST}"
    }

    object Firebase {
        const val BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"
        const val ANALYTICS = "com.google.firebase:firebase-analytics"
        const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics"
    }

    const val COIL_COMPOSE = "io.coil-kt:coil-compose:${Versions.COIL}"
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
    const val MOCKK_ANDROID = "io.mockk:mockk-android:${Versions.MOCKK}"
    const val KOTEST = "io.kotest:kotest-assertions-core:${Versions.KOTEST}"
    const val TURBINE = "app.cash.turbine:turbine:${Versions.TURBINE}"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT}"
    const val CONSTRAIN_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAIN_LAYOUT}"
    const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:${Versions.LEAK_CANARY}"

    object Protobuf {
        const val PROTOC = "com.google.protobuf:protoc:${Versions.PROTOC}"
        const val JAVALITE = "com.google.protobuf:protobuf-javalite:${Versions.PROTOBUF_JAVALITE}"
    }

    object JUnit5 {
        const val BOM = "org.junit:junit-bom:${Versions.JUNIT5}"
        const val JUPITER = "org.junit.jupiter:junit-jupiter"
    }

    object Sql {
        const val SQL_CIPHER = "net.zetetic:android-database-sqlcipher:${Versions.SQL_CIPHER}"
        const val SQL_CORE = "androidx.sqlite:sqlite:${Versions.SQL_CORE}"
    }

    object Room {
        const val ROOM_CORE = "androidx.room:room-ktx:${Versions.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
        const val ROOM_PAGING = "androidx.room:room-paging:${Versions.ROOM}"
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    }

    object Paging {
        const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${Versions.PAGING_RUNTIME}"
        const val PAGING_COMPOSE = "androidx.paging:paging-compose:${Versions.PAGING_COMPOSE}"
        const val PAGING_TEST = "androidx.paging:paging-testing:${Versions.PAGING_RUNTIME}"
        const val PAGING_COMMON = "androidx.paging:paging-common-ktx:${Versions.PAGING_RUNTIME}"
    }

    object Glide {
        const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
        const val GLIDE_COMPILE = "com.github.bumptech.glide:compiler:${Versions.GLIDE_COMPILE}"
    }

    object Material {
        const val MATERIAL_3_COMPOSE = "androidx.compose.material3:material3"
        const val MATERIAL_COMPOSE =
            "androidx.compose.material:material:${Versions.MATERIAL_COMPOSE}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    }

    object DataStore {
        const val DATA_STORE_PROTO = "androidx.datastore:datastore:${Versions.DATA_STORE}"
        const val DATA_STORE_PROTO_CORE = "androidx.datastore:datastore-core:${Versions.DATA_STORE}"
        const val DATA_STORE_PREFERENCES =
            "androidx.datastore:datastore-preferences:${Versions.DATA_STORE}"
    }
}
