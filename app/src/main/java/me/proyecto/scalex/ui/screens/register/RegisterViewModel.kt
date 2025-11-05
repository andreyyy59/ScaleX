package me.proyecto.scalex.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        isEmailValid = validateEmail(event.email)
                    )
                }
            }
            is RegisterEvent.UsernameChanged -> {
                _state.update {
                    it.copy(
                        username = event.username,
                        isUsernameValid = event.username.length >= 3
                    )
                }
            }
            is RegisterEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        isPasswordValid = event.password.length >= 6,
                        isConfirmPasswordValid = if (_state.value.confirmPassword.isNotEmpty()) {
                            event.password == _state.value.confirmPassword
                        } else {
                            true
                        }
                    )
                }
            }
            is RegisterEvent.ConfirmPasswordChanged -> {
                _state.update {
                    it.copy(
                        confirmPassword = event.confirmPassword,
                        isConfirmPasswordValid = event.confirmPassword == _state.value.password
                    )
                }
            }
            is RegisterEvent.Register -> {
                register()
            }
        }
    }

    private fun register() {
        val currentState = _state.value

        // Validaciones
        val isEmailValid = validateEmail(currentState.email)
        val isUsernameValid = currentState.username.length >= 3
        val isPasswordValid = currentState.password.length >= 6
        val isConfirmPasswordValid = currentState.password == currentState.confirmPassword

        _state.update {
            it.copy(
                isEmailValid = isEmailValid,
                isUsernameValid = isUsernameValid,
                isPasswordValid = isPasswordValid,
                isConfirmPasswordValid = isConfirmPasswordValid
            )
        }

        if (!isEmailValid || !isUsernameValid || !isPasswordValid || !isConfirmPasswordValid) {
            return
        }

        // Simular registro
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                // Aquí irá tu lógica de registro con Firebase
                delay(2000) // Simular llamada a API

                // Registro exitoso
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRegisterSuccessful = true
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

sealed class RegisterEvent {
    data class EmailChanged(val email: String) : RegisterEvent()
    data class UsernameChanged(val username: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent()
    object Register : RegisterEvent()
}