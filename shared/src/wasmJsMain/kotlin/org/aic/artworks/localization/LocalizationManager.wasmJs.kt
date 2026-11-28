package org.aic.artworks.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

actual object LocalizationManager {
    actual fun setLocale(language: AppLanguage) {
        setJsLanguage(language.code)
    }
}

private fun setJsLanguage(code: String): Unit = js("""{
    try {
        if (typeof window !== 'undefined' && window.navigator) {
            Object.defineProperty(window.navigator, 'languages', {
                get: function() { return [code]; },
                configurable: true
            });
            Object.defineProperty(window.navigator, 'language', {
                get: function() { return code; },
                configurable: true
            });
        }
        if (typeof document !== 'undefined' && document.documentElement) {
            document.documentElement.lang = code;
        }
    } catch (e) {
        console.error("Failed to update navigator language", e);
    }
}""")

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
