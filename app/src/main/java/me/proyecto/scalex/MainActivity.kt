package me.proyecto.scalex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import me.proyecto.scalex.ui.navigation.NavigationGraph
import me.proyecto.scalex.ui.screens.session.SessionViewModel
import me.proyecto.scalex.ui.theme.ScaleXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScaleXTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val sessionViewModel: SessionViewModel = viewModel()
                    val isUserLoggedIn by sessionViewModel.isUserLoggedIn.collectAsState()

                    LaunchedEffect(Unit) {
                        sessionViewModel.checkSession()
                    }

                    // Espera a que se verifique la sesión antes de cargar la navegación
                    if (isUserLoggedIn != null) {
                        NavigationGraph(
                            navController = navController,
                            startDestination = if (isUserLoggedIn == true) "home" else "login"
                        )
                    }
                }
            }
        }
    }
}
