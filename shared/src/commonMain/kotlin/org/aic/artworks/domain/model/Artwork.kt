package org.aic.artworks.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Artwork(
    val id: Int,
    val title: String,
    val artistDisplay: String,
    val dateDisplay: String,
    val mediumDisplay: String,
    val imageId: String
)
