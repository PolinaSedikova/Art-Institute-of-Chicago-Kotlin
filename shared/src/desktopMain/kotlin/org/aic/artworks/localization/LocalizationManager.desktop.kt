package org.aic.artworks.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import java.util.Locale
import java.util.ResourceBundle

actual object LocalizationManager {
    actual fun setLocale(language: AppLanguage) {
        val locale = Locale(language.code)
        Locale.setDefault(locale)
        ResourceBundle.clearCache()
    }
}

@Composable
actual fun ProvidePlatformLocalization(
    language: AppLanguage,
    content: @Composable () -> Unit
) {
    LocalizationManager.setLocale(language)
    CompositionLocalProvider(
        LocalAppLanguage provides language
    ) {
        content()
    }
}
