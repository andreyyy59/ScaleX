package me.proyecto.scalex.ui.screens.compare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.proyecto.scalex.FavoritesRepository
import me.proyecto.scalex.data.model.Motorcycle
import me.proyecto.scalex.data.repository.MotorcycleRepository

class CompareViewModel(
    private val repository: MotorcycleRepository = MotorcycleRepository(),
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CompareState())
    val state: StateFlow<CompareState> = _state.asStateFlow()

    init {
        loadFavorites()
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
                        motorcycle1 = null
                    )
                }
            }
            is CompareEvent.RemoveMotorcycle2 -> {
                _state.update {
                    it.copy(
                        motorcycle2 = null
                    )
                }
            }
            is CompareEvent.ToggleFavorite -> {
                toggleFavorite(event.motorcycleId)
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

            val result = repository.searchByModel(query.trim())

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

    private fun toggleFavorite(motorcycleId: String) {
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(motorcycleId)
            loadFavorites()
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            val favorites = favoritesRepository.getFavorites()
            _state.update { it.copy(favorites = favorites) }
        }
    }
}

sealed class CompareEvent {
    data class SearchMotorcycles(val query: String) : CompareEvent()
    data class SelectMotorcycle1(val motorcycle: Motorcycle) : CompareEvent()
    data class SelectMotorcycle2(val motorcycle: Motorcycle) : CompareEvent()
    object RemoveMotorcycle1 : CompareEvent()
    object RemoveMotorcycle2 : CompareEvent()
    data class ToggleFavorite(val motorcycleId: String) : CompareEvent()
    object ShowSelector1 : CompareEvent()
    object ShowSelector2 : CompareEvent()
    object HideSelector1 : CompareEvent()
    object HideSelector2 : CompareEvent()
    data class UpdateSearchQuery(val query: String) : CompareEvent()
}