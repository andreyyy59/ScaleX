package me.proyecto.scalex.ui.screens.login

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isLoginSuccessful: Boolean = false
)

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val formState: LoginFormState = LoginFormState()) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

sealed interface LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    object Login : LoginEvent
}
