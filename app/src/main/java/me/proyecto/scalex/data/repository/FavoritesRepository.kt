// data/repository/FavoritesRepository.kt

package me.proyecto.scalex.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.proyecto.scalex.data.model.Motorcycle

class FavoritesRepository(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()

    companion object {
        private const val PREFS_NAME = "scalex_favorites"
        private const val KEY_FAVORITES = "favorites_list"

        // Singleton instance
        @Volatile
        private var instance: FavoritesRepository? = null

        fun getInstance(context: Context): FavoritesRepository {
            return instance ?: synchronized(this) {
                instance ?: FavoritesRepository(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    /**
     * Obtener todas las motos favoritas
     */
    fun getAllFavorites(): List<Motorcycle> {
        val json = sharedPreferences.getString(KEY_FAVORITES, null) ?: return emptyList()
        val type = object : TypeToken<List<Motorcycle>>() {}.type
        return try {
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Agregar una moto a favoritos
     */
    fun addFavorite(motorcycle: Motorcycle): Boolean {
        val currentFavorites = getAllFavorites().toMutableList()

        // Verificar si ya existe
        if (currentFavorites.any { it.getId() == motorcycle.getId() }) {
            return false // Ya está en favoritos
        }

        currentFavorites.add(motorcycle)
        return saveFavorites(currentFavorites)
    }

    /**
     * Eliminar una moto de favoritos
     */
    fun removeFavorite(motorcycleId: String): Boolean {
        val currentFavorites = getAllFavorites().toMutableList()
        val removed = currentFavorites.removeAll { it.getId() == motorcycleId }

        return if (removed) {
            saveFavorites(currentFavorites)
        } else {
            false
        }
    }

    /**
     * Verificar si una moto está en favoritos
     */
    fun isFavorite(motorcycleId: String): Boolean {
        return getAllFavorites().any { it.getId() == motorcycleId }
    }

    /**
     * Obtener una moto favorita por ID
     */
    fun getFavoriteById(motorcycleId: String): Motorcycle? {
        return getAllFavorites().find { it.getId() == motorcycleId }
    }

    /**
     * Limpiar todos los favoritos
     */
    fun clearAllFavorites(): Boolean {
        return sharedPreferences.edit().remove(KEY_FAVORITES).commit()
    }

    /**
     * Obtener cantidad de favoritos
     */
    fun getFavoritesCount(): Int {
        return getAllFavorites().size
    }

    /**
     * Toggle favorito (agregar si no existe, eliminar si existe)
     */
    fun toggleFavorite(motorcycle: Motorcycle): Boolean {
        return if (isFavorite(motorcycle.getId())) {
            removeFavorite(motorcycle.getId())
        } else {
            addFavorite(motorcycle)
        }
    }

    /**
     * Guardar lista de favoritos
     */
    private fun saveFavorites(favorites: List<Motorcycle>): Boolean {
        val json = gson.toJson(favorites)
        return sharedPreferences.edit().putString(KEY_FAVORITES, json).commit()
    }

    /**
     * Obtener IDs de favoritos (útil para verificaciones rápidas)
     */
    fun getFavoriteIds(): Set<String> {
        return getAllFavorites().map { it.getId() }.toSet()
    }
}