package com.truongdc.movie_tmdb.core.navigation

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Creates a custom NavType for Parcelable objects that can be serialized/deserialized using JSON.
 *
 * @param T The type of Parcelable object.
 * @param isNullableAllowed Whether the argument can be null.
 */
inline fun <reified T : Parcelable> parcelableType(
    isNullableAllowed: Boolean = false,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): T? =
        BundleCompat.getParcelable(bundle, key, T::class.java)

    override fun parseValue(value: String): T {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: T): String = Uri.encode(Json.encodeToString(value))

}
