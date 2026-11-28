package org.aic.artworks.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.aic.artworks.data.mock.mockArtworks
import org.aic.artworks.domain.model.Artwork
import org.aic.artworks.domain.repository.ArtworkRepository

class MockArtworkRepository(
    private val data: List<Artwork> = mockArtworks
) : ArtworkRepository {

    override suspend fun getArtworks(): List<Artwork> = withContext(Dispatchers.Default) {
        data
    }

    override suspend fun getArtworkById(id: Int): Artwork? = withContext(Dispatchers.Default) {
        data.find { it.id == id }
    }

    override suspend fun getArtworksByArtist(
        artistDisplay: String,
        excludeArtworkId: Int
    ): List<Artwork> = withContext(Dispatchers.Default) {
        data.filter { artwork ->
            artwork.id != excludeArtworkId && artwork.artistDisplay.equals(artistDisplay, ignoreCase = true)
        }
    }

    override suspend fun searchArtworks(query: String): List<Artwork> = withContext(Dispatchers.Default) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            data
        } else {
            data.filter { artwork ->
                artwork.title.contains(trimmed, ignoreCase = true) ||
                    artwork.artistDisplay.contains(trimmed, ignoreCase = true) ||
                    artwork.mediumDisplay.contains(trimmed, ignoreCase = true) ||
                    artwork.dateDisplay.contains(trimmed, ignoreCase = true)
            }
        }
    }
}
