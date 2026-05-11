package me.proyecto.scalex.ui.screens.compare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.proyecto.scalex.core.Result
import me.proyecto.scalex.domain.model.Motorcycle
import me.proyecto.scalex.domain.usecase.AddFavoriteUseCase
import me.proyecto.scalex.domain.usecase.GetMotorcyclesUseCase
import javax.inject.Inject

@HiltViewModel
class CompareViewModel @Inject constructor(
    private val getMotorcyclesUseCase: GetMotorcyclesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CompareUiState>(CompareUiState.Success())
    val state: StateFlow<CompareUiState> = _state.asStateFlow()

    private val currentState: CompareUiState.Success
        get() = _state.value as? CompareUiState.Success ?: CompareUiState.Success()

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
                    CompareUiState.Success(
                        motorcycle1 = event.motorcycle,
                        showSelector1 = false,
                        searchResults = emptyList(),
                        searchQuery = "",
                        motorcycle2 = (it as? CompareUiState.Success)?.motorcycle2,
                        favorites = (it as? CompareUiState.Success)?.favorites ?: emptySet()
                    )
                }
            }
            is CompareEvent.SelectMotorcycle2 -> {
                _state.update {
                    CompareUiState.Success(
                        motorcycle2 = event.motorcycle,
                        showSelector2 = false,
                        searchResults = emptyList(),
                        searchQuery = "",
                        motorcycle1 = (it as? CompareUiState.Success)?.motorcycle1,
                        favorites = (it as? CompareUiState.Success)?.favorites ?: emptySet()
                    )
                }
            }
            is CompareEvent.RemoveMotorcycle1 -> {
                val s = currentState
                _state.value = s.copy(motorcycle1 = null, showSelector1 = true)
            }
            is CompareEvent.RemoveMotorcycle2 -> {
                val s = currentState
                _state.value = s.copy(motorcycle2 = null, showSelector2 = true)
            }
            is CompareEvent.ToggleFavorite -> {
                toggleFavorite(event.motorcycleId, event.motorcycle)
            }
            is CompareEvent.ShowSelector1 -> {
                val s = currentState
                _state.value = s.copy(showSelector1 = true, searchResults = emptyList(), searchQuery = "")
            }
            is CompareEvent.ShowSelector2 -> {
                val s = currentState
                _state.value = s.copy(showSelector2 = true, searchResults = emptyList(), searchQuery = "")
            }
            is CompareEvent.HideSelector1 -> {
                val s = currentState
                _state.value = s.copy(showSelector1 = false, searchResults = emptyList(), searchQuery = "")
            }
            is CompareEvent.HideSelector2 -> {
                val s = currentState
                _state.value = s.copy(showSelector2 = false, searchResults = emptyList(), searchQuery = "")
            }
            is CompareEvent.UpdateSearchQuery -> {
                val s = currentState
                _state.value = s.copy(searchQuery = event.query)
            }
        }
    }

    private fun searchMotorcycles(query: String) {
        if (query.isBlank()) {
            _state.update { s ->
                (s as? CompareUiState.Success)?.copy(error = "Ingresa un modelo para buscar") ?: s
            }
            return
        }

        viewModelScope.launch {
            val current = currentState
            _state.value = current.copy(isLoading = true, error = null)

            when (val result = getMotorcyclesUseCase.byModel(query.trim())) {
                is Result.Success -> {
                    val motorcycles = result.data
                    val s = currentState
                    _state.value = s.copy(
                        searchResults = motorcycles,
                        isLoading = false,
                        error = if (motorcycles.isEmpty()) "No se encontraron resultados para '$query'" else null
                    )
                }
                is Result.Error -> {
                    val s = currentState
                    _state.value = s.copy(
                        isLoading = false,
                        error = result.exception.message ?: "Error al buscar motocicletas"
                    )
                }
            }
        }
    }

    private fun toggleFavorite(motorcycleId: String, motorcycle: Motorcycle?) {
        viewModelScope.launch {
            motorcycle?.let {
                addFavoriteUseCase.toggle(it)
                loadFavoriteIds()
            }
        }
    }

    private fun loadFavoriteIds() {
        viewModelScope.launch {
            val favoriteIds = addFavoriteUseCase.getIds()
            val s = currentState
            _state.value = s.copy(favorites = favoriteIds)
        }
    }
}
