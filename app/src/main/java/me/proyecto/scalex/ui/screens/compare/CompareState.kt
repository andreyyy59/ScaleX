// ui/screens/compare/CompareState.kt

package me.proyecto.scalex.ui.screens.compare

import me.proyecto.scalex.data.model.Motorcycle

data class CompareState(
    val motorcycle1: Motorcycle? = null,
    val motorcycle2: Motorcycle? = null,
    val searchResults: List<Motorcycle> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val showSelector1: Boolean = false,
    val showSelector2: Boolean = false,
    val favorites: Set<String> = emptySet()
)