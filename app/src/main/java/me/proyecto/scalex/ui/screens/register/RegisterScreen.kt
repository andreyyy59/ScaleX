    package me.proyecto.scalex.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.proyecto.scalex.R
import me.proyecto.scalex.ui.components.CustomButton
import me.proyecto.scalex.ui.components.CustomTextField
import me.proyecto.scalex.ui.theme.BrightRed
import me.proyecto.scalex.ui.theme.DarkBrown
import me.proyecto.scalex.ui.theme.White

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit = {}
) {
    val viewModel: RegisterViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isRegisterSuccessful) {
        if (state.isRegisterSuccessful) {
            onRegisterSuccess()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.background_login),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay oscuro
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.85f)
                        )
                    )
                )
        )

        // Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_scalex),
                contentDescription = "ScaleX Logo",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(180.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Título "REGISTRO"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBrown)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "REGISTRO",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de Email
            CustomTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(RegisterEvent.EmailChanged(it)) },
                label = "CORREO ELECTRONICO",
                isError = !state.isEmailValid && state.email.isNotEmpty(),
                errorMessage = "Correo electrónico inválido",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Campo de Nombre de Usuario
            CustomTextField(
                value = state.username,
                onValueChange = { viewModel.onEvent(RegisterEvent.UsernameChanged(it)) },
                label = "NOMBRE DE USUARIO",
                isError = !state.isUsernameValid && state.username.isNotEmpty(),
                errorMessage = "El nombre de usuario es requerido",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Campo de Contraseña
            CustomTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(RegisterEvent.PasswordChanged(it)) },
                label = "CONTRASEÑA",
                isPassword = true,
                isError = !state.isPasswordValid && state.password.isNotEmpty(),
                errorMessage = "La contraseña debe tener al menos 6 caracteres",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Campo de Confirmar Contraseña
            CustomTextField(
                value = state.confirmPassword,
                onValueChange = { viewModel.onEvent(RegisterEvent.ConfirmPasswordChanged(it)) },
                label = "CONFIRMAR CONTRASEÑA",
                isPassword = true,
                isError = !state.isConfirmPasswordValid && state.confirmPassword.isNotEmpty(),
                errorMessage = "Las contraseñas no coinciden",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                onImeAction = { viewModel.onEvent(RegisterEvent.Register) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de Registro
            CustomButton(
                text = "REGISTRARSE",
                onClick = { viewModel.onEvent(RegisterEvent.Register) },
                enabled = !state.isLoading
            )

            // Indicador de carga
            if (state.isLoading) {
                Spacer(modifier = Modifier.height(20.dp))
                CircularProgressIndicator(
                    color = BrightRed,
                    modifier = Modifier.size(40.dp)
                )
            }

            // Mensaje de error
            state.error?.let { error ->
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para volver al login
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes cuenta?",
                    color = White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                TextButton(
                    onClick = onNavigateToLogin
                ) {
                    Text(
                        text = "Inicia Sesión",
                        color = BrightRed,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}