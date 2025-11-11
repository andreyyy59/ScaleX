package me.proyecto.scalex.ui.screens.compare

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
import me.proyecto.scalex.data.repository.MotorcycleRepository

class CompareViewModel(application: Application) : AndroidViewModel(application) {

    private val motorcycleRepository = MotorcycleRepository()
    private val favoritesRepository = FavoritesRepository.getInstance(application)

    private val _state = MutableStateFlow(CompareState())
    val state: StateFlow<CompareState> = _state.asStateFlow()

    init {
        loadFavoriteIds()
    }

    fun onEvent(event: CompareEvent) {
        when (event) {
            is CompareEvent.SearchMotorcycles -> {
                searchMotorcycles(event.query)
            }
            is CompareEvent.SelectMotorcycle1 -> {
                _state.update {
                    it.copy(
                        motorcycle1 = event.motorcycle,
                        showSelector1 = false,
                        searchResults = emptyList(),
                        searchQuery = ""
                    )
                }
            }
            is CompareEvent.SelectMotorcycle2 -> {
                _state.update {
                    it.copy(
                        motorcycle2 = event.motorcycle,
                        showSelector2 = false,
                        searchResults = emptyList(),
                        searchQuery = ""
                    )
                }
            }
            is CompareEvent.RemoveMotorcycle1 -> {
                _state.update {
                    it.copy(
                        motorcycle1 = null,
                        showSelector1 = true
                    )
                }
            }
            is CompareEvent.RemoveMotorcycle2 -> {
                _state.update {
                    it.copy(
                        motorcycle2 = null,
                        showSelector2 = true
                    )
                }
            }
            is CompareEvent.ToggleFavorite -> {
                toggleFavorite(event.motorcycleId, event.motorcycle)
            }
            is CompareEvent.ShowSelector1 -> {
                _state.update {
                    it.copy(
                        showSelector1 = true,
                        searchResults = emptyList(),
                        searchQuery = ""
                    )
                }
            }
            is CompareEvent.ShowSelector2 -> {
                _state.update {
                    it.copy(
                        showSelector2 = true,
                        searchResults = emptyList(),
                        searchQuery = ""
                    )
                }
            }
            is CompareEvent.HideSelector1 -> {
                _state.update {
                    it.copy(
                        showSelector1 = false,
                        searchResults = emptyList(),
                        searchQuery = ""
                    )
                }
            }
            is CompareEvent.HideSelector2 -> {
                _state.update {
                    it.copy(
                        showSelector2 = false,
                        searchResults = emptyList(),
                        searchQuery = ""
                    )
                }
            }
            is CompareEvent.UpdateSearchQuery -> {
                _state.update { it.copy(searchQuery = event.query) }
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

            val result = motorcycleRepository.searchByModel(query.trim())

            result.fold(
                onSuccess = { motorcycles ->
                    if (motorcycles.isEmpty()) {
                        _state.update {
                            it.copy(
                                searchResults = emptyList(),
                                isLoading = false,
                                error = "No se encontraron resultados para '$query'"
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                searchResults = motorcycles,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Error al buscar motocicletas"
                        )
                    }
                }
            )
        }
    }

    private fun toggleFavorite(motorcycleId: String, motorcycle: Motorcycle?) {
        viewModelScope.launch {
            motorcycle?.let {
                favoritesRepository.toggleFavorite(it)
                loadFavoriteIds()
            }
        }
    }

    private fun loadFavoriteIds() {
        viewModelScope.launch {
            val favoriteIds = favoritesRepository.getFavoriteIds()
            _state.update { it.copy(favorites = favoriteIds) }
        }
    }
}

sealed class CompareEvent {
    data class SearchMotorcycles(val query: String) : CompareEvent()
    data class SelectMotorcycle1(val motorcycle: Motorcycle) : CompareEvent()
    data class SelectMotorcycle2(val motorcycle: Motorcycle) : CompareEvent()
    object RemoveMotorcycle1 : CompareEvent()
    object RemoveMotorcycle2 : CompareEvent()
    data class ToggleFavorite(val motorcycleId: String, val motorcycle: Motorcycle?) : CompareEvent()
    object ShowSelector1 : CompareEvent()
    object ShowSelector2 : CompareEvent()
    object HideSelector1 : CompareEvent()
    object HideSelector2 : CompareEvent()
    data class UpdateSearchQuery(val query: String) : CompareEvent()
}