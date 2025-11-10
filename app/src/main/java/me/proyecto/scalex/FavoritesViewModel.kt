package me.proyecto.scalex


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.proyecto.scalex.data.model.Motorcycle
import me.proyecto.scalex.data.repository.MotorcycleRepository

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val motorcycleRepository: MotorcycleRepository = MotorcycleRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val favoriteIds = favoritesRepository.getFavorites()
                val favoriteMotorcycles = mutableListOf<Motorcycle>()

                for (motorcycleId in favoriteIds) {
                    try {
                        // Buscar la moto por su ID (que contiene make, model, year)
                        val motorcycle = findMotorcycleById(motorcycleId)
                        motorcycle?.let { favoriteMotorcycles.add(it) }
                    } catch (e: Exception) {
                        // Continuar con las demás si hay error en una
                        e.printStackTrace()
                    }
                }

                _state.update {
                    it.copy(
                        favorites = favoriteMotorcycles,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar favoritos: ${e.message}"
                    )
                }
            }
        }
    }

    private suspend fun findMotorcycleById(motorcycleId: String): Motorcycle? {
        // El ID tiene formato: make_model_year (ej: kawasaki_ninja_2023)
        val parts = motorcycleId.split("_")
        if (parts.size >= 3) {
            val make = parts[0]
            val model = parts[1]
            val year = parts[2]

            val result = motorcycleRepository.searchMotorcycles(
                make = make,
                model = model,
                year = year
            )

            return result.getOrNull()?.firstOrNull()
        }
        return null
    }

    fun removeFromFavorites(motorcycleId: String) {
        viewModelScope.launch {
            try {
                favoritesRepository.removeFavorite(motorcycleId)
                // Recargar la lista después de eliminar
                loadFavorites()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Error al eliminar de favoritos: ${e.message}"
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}