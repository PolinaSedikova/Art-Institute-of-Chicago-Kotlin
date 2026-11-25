package org.aic.artworks.presentation.navigation

sealed interface Screen {
    data object List : Screen
    data class Detail(val artworkId: Int) : Screen
}
