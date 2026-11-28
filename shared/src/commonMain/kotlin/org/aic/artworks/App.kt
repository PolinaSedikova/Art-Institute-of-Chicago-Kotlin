package org.aic.artworks

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.aic.artworks.data.repository.MockArtworkRepository
import org.aic.artworks.localization.AppLanguage
import org.aic.artworks.localization.ProvidePlatformLocalization
import org.aic.artworks.presentation.detail.ArtworkDetailScreen
import org.aic.artworks.presentation.detail.ArtworkDetailViewModel
import org.aic.artworks.presentation.list.ArtworkListScreen
import org.aic.artworks.presentation.list.ArtworkListViewModel
import org.aic.artworks.presentation.navigation.Screen
import org.aic.artworks.presentation.navigation.SystemBackHandler
import org.aic.artworks.presentation.theme.AppTheme

@Composable
fun App() {
    val repository = remember { MockArtworkRepository() }

    val initialDarkTheme = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(initialDarkTheme) }
    var currentLanguage by remember { mutableStateOf(AppLanguage.RU) }

    var backStack by remember { mutableStateOf(listOf<Screen>(Screen.List)) }

    val listViewModel = remember { ArtworkListViewModel(repository) }
    val listUiState by listViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    val canGoBack = backStack.size > 1
    val navigateBack = {
        if (canGoBack) {
            backStack = backStack.dropLast(1)
        }
    }

    SystemBackHandler(enabled = canGoBack, onBack = navigateBack)

    ProvidePlatformLocalization(language = currentLanguage) {
        key(currentLanguage) {
            AppTheme(darkTheme = isDarkTheme) {
                when (val currentScreen = backStack.last()) {
                    is Screen.List -> {
                        ArtworkListScreen(
                            uiState = listUiState,
                            listState = listState,
                            onQueryChanged = listViewModel::onQueryChanged,
                            onClearQuery = listViewModel::onClearQuery,
                            onArtworkClick = { id ->
                                backStack = backStack + Screen.Detail(id)
                            },
                            onToggleTheme = {
                                isDarkTheme = !isDarkTheme
                            },
                            onToggleLanguage = {
                                currentLanguage = currentLanguage.next()
                            }
                        )
                    }

                    is Screen.Detail -> {
                        val detailViewModel = remember(currentScreen.artworkId) {
                            ArtworkDetailViewModel(
                                artworkId = currentScreen.artworkId,
                                repository = repository
                            )
                        }
                        val detailUiState by detailViewModel.uiState.collectAsState()

                        ArtworkDetailScreen(
                            uiState = detailUiState,
                            onBackClick = navigateBack,
                            onRelatedArtworkClick = { relatedId ->
                                backStack = backStack + Screen.Detail(relatedId)
                            },
                            onToggleTheme = {
                                isDarkTheme = !isDarkTheme
                            },
                            onToggleLanguage = {
                                currentLanguage = currentLanguage.next()
                            }
                        )
                    }
                }
            }
        }
    }
}
