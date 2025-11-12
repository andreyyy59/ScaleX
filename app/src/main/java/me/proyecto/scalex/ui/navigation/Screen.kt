package me.proyecto.scalex.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Compare : Screen("compare")
    object Favorites : Screen("favorites")
    object SearchSimilar : Screen("search_similar")
}

