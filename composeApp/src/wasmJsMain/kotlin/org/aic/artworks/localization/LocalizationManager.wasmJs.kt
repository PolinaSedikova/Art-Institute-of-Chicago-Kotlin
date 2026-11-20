package org.aic.artworks.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

actual object LocalizationManager {
    actual fun setLocale(language: AppLanguage) {
        // Platform locale update for Web/Wasm
    }
}

@Composable
actual fun ProvidePlatformLocalization(
    language: AppLanguage,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppLanguage provides language
    ) {
        content()
    }
}
