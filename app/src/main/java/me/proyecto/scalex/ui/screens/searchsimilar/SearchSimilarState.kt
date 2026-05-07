package me.proyecto.scalex.ui.screens.searchsimilar

import me.proyecto.scalex.domain.model.Motorcycle

sealed class SearchSimilarUiState {
    object Idle : SearchSimilarUiState()
    object Loading : SearchSimilarUiState()

    data class Success(
        val selectedMotorcycle: Motorcycle? = null,
        val similarMotorcycles: List<Motorcycle> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val showSelector: Boolean = false,
        val searchQuery: String = "",
        val searchResults: List<Motorcycle> = emptyList()
    ) : SearchSimilarUiState()

    data class Error(val message: String) : SearchSimilarUiState()
}
