package me.proyecto.scalex.ui.screens.favorites

import me.proyecto.scalex.data.model.Motorcycle

data class FavoritesState(
    val favorites: List<Motorcycle> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedMotorcycle: Motorcycle? = null
)