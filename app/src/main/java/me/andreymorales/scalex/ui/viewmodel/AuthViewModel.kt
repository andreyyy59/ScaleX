package me.andreymorales.scalex.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import me.andreymorales.scalex.data.repository.AuthRepository

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var loginSuccess = mutableStateOf<Boolean?>(null)

    fun onLoginClick() {
        loginSuccess.value = repository.login(email.value, password.value)
    }
}
