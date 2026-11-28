package org.aic.artworks.presentation.detail

import androidx.compose.runtime.Immutable
import org.aic.artworks.domain.model.Artwork

@Immutable
data class ArtworkDetailUiState(
    val artwork: Artwork? = null,
    val relatedArtworks: List<Artwork> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
