package me.proyecto.scalex.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.proyecto.scalex.domain.usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterFormState())
    val state: StateFlow<RegisterFormState> = _state.asStateFlow()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
                registerUser()
            }
        }
    }

    private fun registerUser() {
        val currentState = _state.value

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

        if (!isEmailValid || !isUsernameValid || !isPasswordValid || !isConfirmPasswordValid) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                auth.createUserWithEmailAndPassword(currentState.email, currentState.password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.let {
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(currentState.username)
                                    .build()
                                it.updateProfile(profileUpdates)
                            }
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isRegisterSuccessful = true
                                )
                            }
                        } else {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = task.exception?.message ?: "Error al registrar usuario"
                                )
                            }
                        }
                    }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error al registrar usuario"
                    )
                }
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
