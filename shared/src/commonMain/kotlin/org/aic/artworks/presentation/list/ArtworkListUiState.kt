package org.aic.artworks.presentation.list

import androidx.compose.runtime.Immutable
import org.aic.artworks.domain.model.Artwork

@Immutable
data class ArtworkListUiState(
    val query: String = "",
    val artworks: List<Artwork> = emptyList(),
    val isLoading: Boolean = false
)
