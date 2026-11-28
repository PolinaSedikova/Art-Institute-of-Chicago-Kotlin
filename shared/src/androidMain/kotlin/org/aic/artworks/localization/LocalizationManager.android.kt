package org.aic.artworks.localization

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

actual object LocalizationManager {
    actual fun setLocale(language: AppLanguage) {
        val locale = Locale(language.code)
        Locale.setDefault(locale)
    }
}

@Composable
actual fun ProvidePlatformLocalization(
    language: AppLanguage,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val currentConfig = LocalConfiguration.current

    val localizedConfiguration = remember(language, currentConfig) {
        Configuration(currentConfig).apply {
            val locale = Locale(language.code)
            setLocale(locale)
            Locale.setDefault(locale)
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(this, context.resources.displayMetrics)
        }
    }

    CompositionLocalProvider(
        LocalConfiguration provides localizedConfiguration,
        LocalAppLanguage provides language
    ) {
        content()
    }
}
