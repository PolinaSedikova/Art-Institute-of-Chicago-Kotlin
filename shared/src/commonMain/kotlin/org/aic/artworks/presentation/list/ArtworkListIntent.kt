package org.aic.artworks.presentation.list

sealed interface ArtworkListIntent {
    data class QueryChanged(val query: String) : ArtworkListIntent
    data object ClearQuery : ArtworkListIntent
    data class CardClicked(val id: Int) : ArtworkListIntent
}
