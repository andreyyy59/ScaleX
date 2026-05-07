package me.proyecto.scalex.domain.repository

import me.proyecto.scalex.domain.model.Motorcycle

interface IUserRepository {
    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
    fun logout()
    fun isUserLoggedIn(): Boolean
    val currentUserId: String?

    suspend fun getAllFavorites(): List<Motorcycle>
    suspend fun addFavorite(motorcycle: Motorcycle): Boolean
    suspend fun removeFavorite(motorcycleId: String): Boolean
    suspend fun isFavorite(motorcycleId: String): Boolean
    suspend fun getFavoriteIds(): Set<String>
    suspend fun toggleFavorite(motorcycle: Motorcycle): Boolean
}
