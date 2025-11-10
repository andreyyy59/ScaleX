package me.proyecto.scalex.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.proyecto.scalex.FavoritesRepository
import me.proyecto.scalex.FavoritesViewModel
import me.proyecto.scalex.ui.screens.compare.CompareViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CompareViewModel::class.java) -> {
                val favoritesRepository = FavoritesRepository(context)
                CompareViewModel(favoritesRepository = favoritesRepository) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                val favoritesRepository = FavoritesRepository(context)
                FavoritesViewModel(favoritesRepository = favoritesRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
        }
    }
}