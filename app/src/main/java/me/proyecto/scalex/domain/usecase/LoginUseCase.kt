package me.proyecto.scalex.domain.usecase

import me.proyecto.scalex.domain.repository.IUserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend fun execute(email: String, password: String) {
        userRepository.login(email, password)
    }

    fun isLoggedIn(): Boolean {
        return userRepository.isUserLoggedIn()
    }

    fun logout() {
        userRepository.logout()
    }
}
