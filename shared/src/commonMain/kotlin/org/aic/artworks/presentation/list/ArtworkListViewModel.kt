package org.aic.artworks.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.aic.artworks.domain.repository.ArtworkRepository

class ArtworkListViewModel(
    private val repository: ArtworkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtworkListUiState(isLoading = true))
    val uiState: StateFlow<ArtworkListUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadArtworks()
    }

    fun loadArtworks() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val list = repository.getArtworks()
            _uiState.update { it.copy(artworks = list, isLoading = false) }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val results = repository.searchArtworks(newQuery)
            if (_uiState.value.query == newQuery) {
                _uiState.update { it.copy(artworks = results, isLoading = false) }
            }
        }
    }

    fun onClearQuery() {
        onQueryChanged("")
    }

    fun onIntent(intent: ArtworkListIntent) {
        when (intent) {
            is ArtworkListIntent.QueryChanged -> onQueryChanged(intent.query)
            is ArtworkListIntent.ClearQuery -> onClearQuery()
            is ArtworkListIntent.CardClicked -> Unit
        }
    }
}
