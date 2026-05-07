package me.proyecto.scalex.ui.screens.searchsimilar

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
import me.proyecto.scalex.domain.usecase.CompareMotorcyclesUseCase
import me.proyecto.scalex.domain.usecase.GetMotorcyclesUseCase
import javax.inject.Inject

@HiltViewModel
class SearchSimilarViewModel @Inject constructor(
    private val getMotorcyclesUseCase: GetMotorcyclesUseCase,
    private val compareMotorcyclesUseCase: CompareMotorcyclesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SearchSimilarUiState>(SearchSimilarUiState.Idle)
    val state: StateFlow<SearchSimilarUiState> = _state.asStateFlow()

    private val currentState: SearchSimilarUiState.Success
        get() = _state.value as? SearchSimilarUiState.Success ?: SearchSimilarUiState.Success()

    fun onEvent(event: SearchSimilarEvent) {
        when (event) {
            is SearchSimilarEvent.SearchMotorcycles -> {
                searchMotorcycles(event.query)
            }
            is SearchSimilarEvent.SelectMotorcycle -> {
                selectMotorcycle(event.motorcycle)
            }
            is SearchSimilarEvent.ShowSelector -> {
                val s = currentState
                _state.value = s.copy(showSelector = true)
            }
            is SearchSimilarEvent.HideSelector -> {
                val s = currentState
                _state.value = s.copy(showSelector = false, searchResults = emptyList(), searchQuery = "")
            }
            is SearchSimilarEvent.UpdateSearchQuery -> {
                val s = currentState
                _state.value = s.copy(searchQuery = event.query)
            }
            is SearchSimilarEvent.ClearSelection -> {
                _state.value = SearchSimilarUiState.Idle
            }
        }
    }

    private fun searchMotorcycles(query: String) {
        if (query.isBlank()) {
            val s = currentState
            _state.value = s.copy(error = "Ingresa un modelo para buscar")
            return
        }

        viewModelScope.launch {
            _state.value = SearchSimilarUiState.Loading

            when (val result = getMotorcyclesUseCase.byModel(query.trim())) {
                is Result.Success -> {
                    val motorcycles = result.data
                    val s = currentState
                    _state.value = s.copy(
                        searchResults = motorcycles,
                        isLoading = false,
                        error = if (motorcycles.isEmpty()) "No se encontraron resultados" else null
                    )
                }
                is Result.Error -> {
                    val s = currentState
                    _state.value = s.copy(
                        isLoading = false,
                        error = result.exception.message ?: "Error al buscar"
                    )
                }
            }
        }
    }

    private fun selectMotorcycle(motorcycle: Motorcycle) {
        val s = currentState
        _state.value = s.copy(
            selectedMotorcycle = motorcycle,
            showSelector = false,
            searchResults = emptyList(),
            searchQuery = ""
        )
        findSimilarMotorcycles(motorcycle)
    }

    private fun findSimilarMotorcycles(motorcycle: Motorcycle) {
        viewModelScope.launch {
            _state.value = SearchSimilarUiState.Loading

            try {
                val makes = listOf("Kawasaki", "Yamaha", "Honda", "Suzuki", "KTM", "Ducati", "BMW")
                val allMotorcycles = mutableListOf<Motorcycle>()

                for (make in makes) {
                    if (make.equals(motorcycle.make, ignoreCase = true)) continue
                    when (val result = getMotorcyclesUseCase.byMake(make)) {
                        is Result.Success -> allMotorcycles.addAll(result.data)
                        is Result.Error -> { }
                    }
                }

                val similar = compareMotorcyclesUseCase.findSimilar(motorcycle, allMotorcycles, 10)
                val s = currentState
                _state.value = s.copy(
                    similarMotorcycles = similar,
                    isLoading = false
                )
            } catch (e: Exception) {
                val s = currentState
                _state.value = s.copy(
                    isLoading = false,
                    error = "Error al buscar similares: ${e.message}"
                )
            }
        }
    }
}
