package org.aic.artworks.domain.repository

import org.aic.artworks.domain.model.Artwork

interface ArtworkRepository {
    suspend fun getArtworks(): List<Artwork>
    suspend fun getArtworkById(id: Int): Artwork?
    suspend fun getArtworksByArtist(artistDisplay: String, excludeArtworkId: Int): List<Artwork>
    suspend fun searchArtworks(query: String): List<Artwork>
}
