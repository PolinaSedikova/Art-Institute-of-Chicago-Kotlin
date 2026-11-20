package org.aic.artworks.localization

enum class AppLanguage(val code: String, val title: String) {
    EN("en", "EN"),
    RU("ru", "RU");

    fun next(): AppLanguage = if (this == EN) RU else EN
}
