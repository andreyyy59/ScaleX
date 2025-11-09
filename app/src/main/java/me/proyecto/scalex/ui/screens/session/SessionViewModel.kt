package me.proyecto.scalex.ui.screens.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.proyecto.scalex.data.repository.AuthRepository

class SessionViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn: StateFlow<Boolean?> = _isUserLoggedIn

    fun checkSession() {
        viewModelScope.launch {
            _isUserLoggedIn.value = repository.isUserLoggedIn()
        }
    }

    fun logout() {
        repository.logout()
        _isUserLoggedIn.value = false
    }
}

