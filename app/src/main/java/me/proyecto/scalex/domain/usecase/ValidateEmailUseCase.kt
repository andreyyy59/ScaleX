package me.proyecto.scalex.domain.usecase

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    fun execute(email: String): Boolean {
        return EMAIL_REGEX.matches(email)
    }

    companion object {
        private val EMAIL_REGEX = Regex(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )
    }
}
