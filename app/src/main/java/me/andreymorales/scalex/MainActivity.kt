package me.andreymorales.scalex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.andreymorales.scalex.ui.theme.ScaleXTheme
import me.andreymorales.scalex.ui.view.LoginScreen
import me.andreymorales.scalex.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = AuthViewModel()

        setContent {
            ScaleXTheme {
                LoginScreen(viewModel)
            }
        }
    }
}
