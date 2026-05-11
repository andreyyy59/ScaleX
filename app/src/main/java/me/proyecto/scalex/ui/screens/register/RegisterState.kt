package me.proyecto.scalex.ui.screens.register

data class RegisterFormState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmailValid: Boolean = true,
    val isUsernameValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isConfirmPasswordValid: Boolean = true,
    val isRegisterSuccessful: Boolean = false
)

sealed interface RegisterEvent {
    data class EmailChanged(val email: String) : RegisterEvent
    data class UsernameChanged(val username: String) : RegisterEvent
    data class PasswordChanged(val password: String) : RegisterEvent
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent
    object Register : RegisterEvent
}

