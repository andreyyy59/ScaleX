package me.proyecto.scalex.domain.usecase

import me.proyecto.scalex.domain.model.Motorcycle
import me.proyecto.scalex.domain.repository.IUserRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {
    suspend fun execute(motorcycle: Motorcycle): Boolean {
        return userRepository.addFavorite(motorcycle)
    }

    suspend fun remove(motorcycleId: String): Boolean {
        return userRepository.removeFavorite(motorcycleId)
    }

    suspend fun isFavorite(motorcycleId: String): Boolean {
        return userRepository.isFavorite(motorcycleId)
    }

    suspend fun getAll(): List<Motorcycle> {
        return userRepository.getAllFavorites()
    }

    suspend fun toggle(motorcycle: Motorcycle): Boolean {
        return userRepository.toggleFavorite(motorcycle)
    }

    suspend fun getIds(): Set<String> {
        return userRepository.getFavoriteIds()
    }
}
