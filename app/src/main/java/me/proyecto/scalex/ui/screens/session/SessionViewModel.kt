package me.proyecto.scalex.ui.screens.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.proyecto.scalex.domain.usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn: StateFlow<Boolean?> = _isUserLoggedIn

    fun checkSession() {
        viewModelScope.launch {
            _isUserLoggedIn.value = loginUseCase.isLoggedIn()
        }
    }

    fun logout() {
        loginUseCase.logout()
        _isUserLoggedIn.value = false
    }
}
