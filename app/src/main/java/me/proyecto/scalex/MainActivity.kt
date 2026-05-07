package me.proyecto.scalex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.proyecto.scalex.ui.navigation.NavigationGraph
import me.proyecto.scalex.ui.screens.session.SessionViewModel
import me.proyecto.scalex.ui.theme.ScaleXTheme

@AndroidEntryPoint
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
                    val sessionViewModel: SessionViewModel = hiltViewModel()
                    val isUserLoggedIn by sessionViewModel.isUserLoggedIn.collectAsStateWithLifecycle()

                    LaunchedEffect(Unit) {
                        sessionViewModel.checkSession()
                    }

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
