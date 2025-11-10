package me.proyecto.scalex

import me.proyecto.scalex.data.model.Motorcycle

data class FavoritesState(
    val favorites: List<Motorcycle> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)