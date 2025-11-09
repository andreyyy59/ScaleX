package me.proyecto.scalex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import me.proyecto.scalex.ui.screens.home.HomeScreen
import me.proyecto.scalex.ui.screens.login.LoginScreen
import me.proyecto.scalex.ui.screens.register.RegisterScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String
) {
    // Verifica si el usuario ya está autenticado
    val user = FirebaseAuth.getInstance().currentUser
    val startDestination = if (user != null) Screen.Home.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onLogout = {
                    FirebaseAuth.getInstance().signOut() // 🔥 Cierra sesión
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToCompare = {
                    // TODO: navegar a pantalla de comparación
                },
                onNavigateToFavorites = {
                    // TODO: navegar a pantalla de favoritos
                },
                onNavigateToSearch = {
                    // TODO: navegar a pantalla de búsqueda
                }
            )
        }
    }
}
