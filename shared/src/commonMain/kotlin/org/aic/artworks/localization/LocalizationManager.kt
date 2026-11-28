package org.aic.artworks.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppLanguage = staticCompositionLocalOf { AppLanguage.EN }

expect object LocalizationManager {
    fun setLocale(language: AppLanguage)
}

@Composable
expect fun ProvidePlatformLocalization(
    language: AppLanguage,
    content: @Composable () -> Unit
)
