package me.proyecto.scalex.ui.screens.compare

import me.proyecto.scalex.domain.model.Motorcycle

sealed class CompareUiState {
    object Loading : CompareUiState()

    data class Success(
        val motorcycle1: Motorcycle? = null,
        val motorcycle2: Motorcycle? = null,
        val searchResults: List<Motorcycle> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val searchQuery: String = "",
        val showSelector1: Boolean = false,
        val showSelector2: Boolean = false,
        val favorites: Set<String> = emptySet()
    ) : CompareUiState()

    data class Error(val message: String) : CompareUiState()
}
