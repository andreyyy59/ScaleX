package me.proyecto.scalex.ui.screens.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.proyecto.scalex.data.model.Motorcycle
import me.proyecto.scalex.data.repository.FavoritesRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritesRepository = FavoritesRepository.getInstance(application)

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    init {
        loadFavorites()
    }

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.RemoveFavorite -> {
                removeFavorite(event.motorcycleId)
            }
            is FavoritesEvent.SelectMotorcycle -> {
                _state.update { it.copy(selectedMotorcycle = event.motorcycle) }
            }
            is FavoritesEvent.DeselectMotorcycle -> {
                _state.update { it.copy(selectedMotorcycle = null) }
            }
            is FavoritesEvent.AddToComparison -> {
                // TODO: Navegar a compare con la moto seleccionada
            }
            is FavoritesEvent.Refresh -> {
                loadFavorites()
            }
            is FavoritesEvent.ClearAll -> {
                clearAllFavorites()
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val favorites = favoritesRepository.getAllFavorites()

                _state.update {
                    it.copy(
                        favorites = favorites,
                        isLoading = false,
                        error = if (favorites.isEmpty()) null else null
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

    private fun removeFavorite(motorcycleId: String) {
        viewModelScope.launch {
            val success = favoritesRepository.removeFavorite(motorcycleId)

            if (success) {
                // Actualizar el estado local
                _state.update { currentState ->
                    currentState.copy(
                        favorites = currentState.favorites.filter { it.getId() != motorcycleId }
                    )
                }
            }
        }
    }

    private fun clearAllFavorites() {
        viewModelScope.launch {
            val success = favoritesRepository.clearAllFavorites()

            if (success) {
                _state.update { it.copy(favorites = emptyList()) }
            }
        }
    }

    fun addFavorite(motorcycle: Motorcycle) {
        viewModelScope.launch {
            val success = favoritesRepository.addFavorite(motorcycle)

            if (success) {
                loadFavorites() // Recargar la lista
            }
        }
    }

    fun isFavorite(motorcycleId: String): Boolean {
        return favoritesRepository.isFavorite(motorcycleId)
    }
}

sealed class FavoritesEvent {
    data class RemoveFavorite(val motorcycleId: String) : FavoritesEvent()
    data class SelectMotorcycle(val motorcycle: Motorcycle) : FavoritesEvent()
    object DeselectMotorcycle : FavoritesEvent()
    data class AddToComparison(val motorcycle: Motorcycle) : FavoritesEvent()
    object Refresh : FavoritesEvent()
    object ClearAll : FavoritesEvent()
}