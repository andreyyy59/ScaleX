package me.proyecto.scalex.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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

        if (!isEmailValid || !isPasswordValid) {
            return
        }

        // 🔥 Autenticación con Firebase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            auth.signInWithEmailAndPassword(currentState.email, currentState.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isLoginSuccessful = true
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = task.exception?.message ?: "Error al iniciar sesión"
                            )
                        }
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
