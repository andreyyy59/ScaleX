package me.proyecto.scalex.ui.screens.favorites

import me.proyecto.scalex.domain.model.Motorcycle

sealed class FavoritesUiState {
    object Loading : FavoritesUiState()

    data class Success(
        val favorites: List<Motorcycle> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val selectedMotorcycle: Motorcycle? = null
    ) : FavoritesUiState()

    data class Error(val message: String) : FavoritesUiState()
}

sealed interface FavoritesEvent {
    data class RemoveFavorite(val motorcycleId: String) : FavoritesEvent
    data class SelectMotorcycle(val motorcycle: Motorcycle) : FavoritesEvent
    object DeselectMotorcycle : FavoritesEvent
    object AddToComparison : FavoritesEvent
    object Refresh : FavoritesEvent
    object ClearAll : FavoritesEvent
}
