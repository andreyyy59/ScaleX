package me.proyecto.scalex

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesRepository(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "scalex_favorites"
        private const val KEY_FAVORITES = "favorite_motorcycle_ids"
    }

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    suspend fun addFavorite(motorcycleId: String) {
        withContext(Dispatchers.IO) {
            val currentFavorites = getFavorites().toMutableSet()
            currentFavorites.add(motorcycleId)
            saveFavorites(currentFavorites)
        }
    }

    suspend fun removeFavorite(motorcycleId: String) {
        withContext(Dispatchers.IO) {
            val currentFavorites = getFavorites().toMutableSet()
            currentFavorites.remove(motorcycleId)
            saveFavorites(currentFavorites)
        }
    }

    suspend fun toggleFavorite(motorcycleId: String) {
        withContext(Dispatchers.IO) {
            val currentFavorites = getFavorites().toMutableSet()
            if (motorcycleId in currentFavorites) {
                currentFavorites.remove(motorcycleId)
            } else {
                currentFavorites.add(motorcycleId)
            }
            saveFavorites(currentFavorites)
        }
    }

    fun getFavorites(): Set<String> {
        return preferences.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }

    fun isFavorite(motorcycleId: String): Boolean {
        return motorcycleId in getFavorites()
    }

    private fun saveFavorites(favorites: Set<String>) {
        preferences.edit()
            .putStringSet(KEY_FAVORITES, favorites)
            .apply()
    }
}