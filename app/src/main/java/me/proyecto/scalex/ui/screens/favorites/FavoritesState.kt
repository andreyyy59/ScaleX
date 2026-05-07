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
