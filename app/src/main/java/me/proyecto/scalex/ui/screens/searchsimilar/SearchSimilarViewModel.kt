package me.proyecto.scalex.ui.screens.searchsimilar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.proyecto.scalex.data.model.Motorcycle
import me.proyecto.scalex.data.repository.MotorcycleRepository
import kotlin.math.abs

class SearchSimilarViewModel(
    private val repository: MotorcycleRepository = MotorcycleRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(SearchSimilarState())
    val state: StateFlow<SearchSimilarState> = _state.asStateFlow()

    fun onEvent(event: SearchSimilarEvent) {
        when (event) {
            is SearchSimilarEvent.SearchMotorcycles -> {
                searchMotorcycles(event.query)
            }
            is SearchSimilarEvent.SelectMotorcycle -> {
                selectMotorcycle(event.motorcycle)
            }
            is SearchSimilarEvent.ShowSelector -> {
                _state.update { it.copy(showSelector = true) }
            }
            is SearchSimilarEvent.HideSelector -> {
                _state.update { it.copy(showSelector = false, searchResults = emptyList(), searchQuery = "") }
            }
            is SearchSimilarEvent.UpdateSearchQuery -> {
                _state.update { it.copy(searchQuery = event.query) }
            }
            is SearchSimilarEvent.ClearSelection -> {
                _state.update {
                    it.copy(
                        selectedMotorcycle = null,
                        similarMotorcycles = emptyList()
                    )
                }
            }
        }
    }

    private fun searchMotorcycles(query: String) {
        if (query.isBlank()) {
            _state.update { it.copy(error = "Ingresa un modelo para buscar") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = repository.searchByModel(query.trim())

            result.fold(
                onSuccess = { motorcycles ->
                    _state.update {
                        it.copy(
                            searchResults = motorcycles,
                            isLoading = false,
                            error = if (motorcycles.isEmpty()) "No se encontraron resultados" else null
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Error al buscar"
                        )
                    }
                }
            )
        }
    }

    private fun selectMotorcycle(motorcycle: Motorcycle) {
        _state.update {
            it.copy(
                selectedMotorcycle = motorcycle,
                showSelector = false,
                searchResults = emptyList(),
                searchQuery = ""
            )
        }
        findSimilarMotorcycles(motorcycle)
    }

    private fun findSimilarMotorcycles(motorcycle: Motorcycle) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                // Buscar motos de diferentes marcas
                val allMotorcycles = mutableListOf<Motorcycle>()

                val makes = listOf("Kawasaki", "Yamaha", "Honda", "Suzuki", "KTM", "Ducati", "BMW")

                for (make in makes) {
                    if (make.equals(motorcycle.make, ignoreCase = true)) continue

                    val result = repository.searchByMake(make)
                    result.fold(
                        onSuccess = { bikes -> allMotorcycles.addAll(bikes) },
                        onFailure = { }
                    )
                }

                // Filtrar y ordenar por similitud
                val similar = allMotorcycles
                    .filter { it.getId() != motorcycle.getId() }
                    .map { bike -> bike to calculateSimilarity(motorcycle, bike) }
                    .sortedByDescending { it.second }
                    .take(10)
                    .map { it.first }

                _state.update {
                    it.copy(
                        similarMotorcycles = similar,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al buscar similares: ${e.message}"
                    )
                }
            }
        }
    }

    private fun calculateSimilarity(base: Motorcycle, compare: Motorcycle): Double {
        var score = 0.0
        var factors = 0

        // Comparar dimensiones (peso 40%)
        base.getLengthInMm()?.let { baseLength ->
            compare.getLengthInMm()?.let { compareLength ->
                val diff = abs(baseLength - compareLength)
                score += (1.0 - (diff / baseLength).coerceIn(0.0F, 1.0F)) * 0.15
                factors++
            }
        }

        base.getWidthInMm()?.let { baseWidth ->
            compare.getWidthInMm()?.let { compareWidth ->
                val diff = abs(baseWidth - compareWidth)
                score += (1.0 - (diff / baseWidth).coerceIn(0.0F, 1.0F)) * 0.15
                factors++
            }
        }

        base.getHeightInMm()?.let { baseHeight ->
            compare.getHeightInMm()?.let { compareHeight ->
                val diff = abs(baseHeight - compareHeight)
                score += (1.0 - (diff / baseHeight).coerceIn(0.0F, 1.0F)) * 0.10
                factors++
            }
        }

        // Comparar peso (peso 20%)
        base.getWeightInKg()?.let { baseWeight ->
            compare.getWeightInKg()?.let { compareWeight ->
                val diff = abs(baseWeight - compareWeight)
                score += (1.0 - (diff / baseWeight).coerceIn(0.0F, 1.0F)) * 0.20
                factors++
            }
        }

        // Comparar cilindrada (peso 20%)
        base.getDisplacementInCC()?.let { baseDisp ->
            compare.getDisplacementInCC()?.let { compareDisp ->
                val diff = abs(baseDisp - compareDisp)
                score += (1.0 - (diff / baseDisp).coerceIn(0.0F, 1.0F)) * 0.20
                factors++
            }
        }

        // Comparar potencia (peso 20%)
        base.getPowerInHP()?.let { basePower ->
            compare.getPowerInHP()?.let { comparePower ->
                val diff = abs(basePower - comparePower)
                score += (1.0 - (diff / basePower).coerceIn(0.0F, 1.0F)) * 0.20
                factors++
            }
        }

        return if (factors > 0) score else 0.0
    }
}

sealed class SearchSimilarEvent {
    data class SearchMotorcycles(val query: String) : SearchSimilarEvent()
    data class SelectMotorcycle(val motorcycle: Motorcycle) : SearchSimilarEvent()
    object ShowSelector : SearchSimilarEvent()
    object HideSelector : SearchSimilarEvent()
    data class UpdateSearchQuery(val query: String) : SearchSimilarEvent()
    object ClearSelection : SearchSimilarEvent()
}
