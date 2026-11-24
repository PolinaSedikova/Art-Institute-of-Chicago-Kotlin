package org.aic.artworks.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.aic.artworks.domain.repository.ArtworkRepository

class ArtworkDetailViewModel(
    private val artworkId: Int,
    private val repository: ArtworkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtworkDetailUiState(isLoading = true))
    val uiState: StateFlow<ArtworkDetailUiState> = _uiState.asStateFlow()

    init {
        loadArtworkDetails()
    }

    fun loadArtworkDetails() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val item = repository.getArtworkById(artworkId)
            if (item != null) {
                val related = repository.getArtworksByArtist(
                    artistDisplay = item.artistDisplay,
                    excludeArtworkId = item.id
                )
                _uiState.update {
                    it.copy(
                        artwork = item,
                        relatedArtworks = related,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Artwork not found"
                    )
                }
            }
        }
    }
}
