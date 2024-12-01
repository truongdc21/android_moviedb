package com.truongdc.movie_tmdb.core.model


enum class Language(val code: String) {
    EN("en"),
    VI("vi"),
    JA("ja");
    companion object {
        fun fromCode(code: String): Language {
            return entries.find { it.code.equals(code, ignoreCase = true) } ?: EN
        }
    }
}
