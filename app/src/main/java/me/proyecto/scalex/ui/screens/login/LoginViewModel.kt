package me.proyecto.scalex.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        isEmailValid = validateEmail(event.email)
                    )
                }
            }
            is LoginEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        isPasswordValid = event.password.isNotEmpty()
                    )
                }
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        val currentState = _state.value

        // Validaciones
        val isEmailValid = validateEmail(currentState.email)
        val isPasswordValid = currentState.password.isNotEmpty()

        _state.update {
            it.copy(
                isEmailValid = isEmailValid,
                isPasswordValid = isPasswordValid
            )
        }

        if (!isEmailValid || !isPasswordValid) {
            return
        }

        // Simular login
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                // Aquí irá tu lógica de login (API, Firebase, etc.)
                kotlinx.coroutines.delay(2000) // Simular llamada a API

                // Login exitoso
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccessful = true
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

// Eventos del Login
sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Login : LoginEvent()
}