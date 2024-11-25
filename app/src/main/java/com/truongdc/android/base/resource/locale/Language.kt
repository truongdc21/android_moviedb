package com.truongdc.android.base.resource.locale

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import com.truongdc.android.base.R

enum class Language(val languageCode: String) {
    EN("en"),
    VI("vi"),
    JA("ja");

    fun getDisplayName(context: Context): String {
        return when (this) {
            EN -> context.getString(R.string.en)
            VI -> context.getString(R.string.vi)
            JA -> context.getString(R.string.ja)
        }
    }

    companion object {
        fun fromCode(code: String): Language {
            return entries.find { it.languageCode.equals(code, ignoreCase = true) } ?: EN
        }
    }
}


/**
 * A composition local for [Language].
 */
val LocalLanguages = compositionLocalOf {
    Language.EN
}
