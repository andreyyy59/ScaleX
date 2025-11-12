package me.proyecto.scalex.ui.screens.searchsimilar

import me.proyecto.scalex.data.model.Motorcycle

data class SearchSimilarState(
    val selectedMotorcycle: Motorcycle? = null,
    val similarMotorcycles: List<Motorcycle> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showSelector: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<Motorcycle> = emptyList()
)
