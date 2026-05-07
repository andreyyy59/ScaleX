package me.proyecto.scalex.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.proyecto.scalex.domain.model.Motorcycle
import me.proyecto.scalex.domain.usecase.AddFavoriteUseCase
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val addFavoriteUseCase: AddFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val state: StateFlow<FavoritesUiState> = _state.asStateFlow()

    private val currentState: FavoritesUiState.Success
        get() = _state.value as? FavoritesUiState.Success ?: FavoritesUiState.Success()

    init {
        loadFavorites()
    }

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.RemoveFavorite -> {
                removeFavorite(event.motorcycleId)
            }
            is FavoritesEvent.SelectMotorcycle -> {
                val s = currentState
                _state.value = s.copy(selectedMotorcycle = event.motorcycle)
            }
            is FavoritesEvent.DeselectMotorcycle -> {
                val s = currentState
                _state.value = s.copy(selectedMotorcycle = null)
            }
            is FavoritesEvent.AddToComparison -> {
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
            _state.value = FavoritesUiState.Loading
            try {
                val favorites = addFavoriteUseCase.getAll()
                _state.value = FavoritesUiState.Success(
                    favorites = favorites,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = FavoritesUiState.Error(
                    message = "Error al cargar favoritos: ${e.message}"
                )
            }
        }
    }

    private fun removeFavorite(motorcycleId: String) {
        viewModelScope.launch {
            val success = addFavoriteUseCase.remove(motorcycleId)
            if (success) {
                loadFavorites()
            } else {
                val s = currentState
                _state.value = s.copy(error = "Error al eliminar favorito")
            }
        }
    }

    private fun clearAllFavorites() {
        viewModelScope.launch {
            // Note: clearAll is not directly available, we iterate
            val favorites = addFavoriteUseCase.getAll()
            favorites.forEach { addFavoriteUseCase.remove(it.getId()) }
            loadFavorites()
        }
    }

    fun addFavorite(motorcycle: Motorcycle) {
        viewModelScope.launch {
            val success = addFavoriteUseCase.execute(motorcycle)
            if (success) {
                loadFavorites()
            } else {
                val s = currentState
                _state.value = s.copy(error = "Error al agregar favorito")
            }
        }
    }

    fun isFavorite(motorcycleId: String): Boolean {
        val s = currentState
        return s.favorites.any { it.getId() == motorcycleId }
    }
}
