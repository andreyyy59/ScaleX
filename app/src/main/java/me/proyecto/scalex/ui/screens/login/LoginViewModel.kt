package me.proyecto.scalex.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.proyecto.scalex.domain.usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginFormState())
    val state: StateFlow<LoginFormState> = _state.asStateFlow()

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
                loginUser()
            }
        }
    }

    private fun loginUser() {
        val currentState = _state.value

        val isEmailValid = validateEmail(currentState.email)
        val isPasswordValid = currentState.password.isNotEmpty()

        _state.update {
            it.copy(
                isEmailValid = isEmailValid,
                isPasswordValid = isPasswordValid
            )
        }

        if (!isEmailValid || !isPasswordValid) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                loginUseCase.execute(currentState.email, currentState.password)
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
                        error = e.message ?: "Error al iniciar sesión"
                    )
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
