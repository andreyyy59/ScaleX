package me.andreymorales.scalex.data.repository

class AuthRepository {
    fun login(email: String, password: String): Boolean {
        // Simulación: usuario válido
        return email == "admin@scalex.com" && password == "1234"
    }
}