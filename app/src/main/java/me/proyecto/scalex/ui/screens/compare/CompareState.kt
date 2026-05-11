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

sealed interface CompareEvent {
    data class SearchMotorcycles(val query: String) : CompareEvent
    data class SelectMotorcycle1(val motorcycle: Motorcycle) : CompareEvent
    data class SelectMotorcycle2(val motorcycle: Motorcycle) : CompareEvent
    object RemoveMotorcycle1 : CompareEvent
    object RemoveMotorcycle2 : CompareEvent
    data class ToggleFavorite(val motorcycleId: String, val motorcycle: Motorcycle?) : CompareEvent
    object ShowSelector1 : CompareEvent
    object ShowSelector2 : CompareEvent
    object HideSelector1 : CompareEvent
    object HideSelector2 : CompareEvent
    data class UpdateSearchQuery(val query: String) : CompareEvent
}
