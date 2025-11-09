package me.proyecto.scalex.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.proyecto.scalex.R
import me.proyecto.scalex.ui.components.CustomButton
import me.proyecto.scalex.ui.components.CustomTextField
import me.proyecto.scalex.ui.theme.BrightRed
import me.proyecto.scalex.ui.theme.DarkBrown
import me.proyecto.scalex.ui.theme.ScaleXTheme
import me.proyecto.scalex.ui.theme.White

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit = {}
) {
    val viewModel: LoginViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isLoginSuccessful) {
        if (state.isLoginSuccessful) onLoginSuccess()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_login),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.85f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            //Logo
            Image(
                painter = painterResource(id = R.drawable.logo_scalex),
                contentDescription = "ScaleX Logo",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(40.dp))

            //Box Titulo inicio de sesion
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3B0D0D))
                    .drawBehind {
                        // Línea superior
                        drawLine(
                            color = Color.White,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 4f
                        )
                        // Línea inferior
                        drawLine(
                            color = Color.White,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 4f
                        )
                    }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "INICIO DE SESIÓN",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }


            Spacer(modifier = Modifier.height(32.dp))

            CustomTextField(
                value = state.email,
                onValueChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
                label = "CORREO ELECTRONICO",
                isError = !state.isEmailValid && state.email.isNotEmpty(),
                errorMessage = "Correo electrónico inválido",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                modifier = Modifier.padding(horizontal = 32.dp)            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
                label = "CONTRASEÑA",
                isPassword = true,
                isError = !state.isPasswordValid && state.password.isNotEmpty(),
                errorMessage = "La contraseña no puede estar vacía",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                onImeAction = { viewModel.onEvent(LoginEvent.Login) } ,
                modifier = Modifier.padding(horizontal = 32.dp)            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomButton(
                text = "INICIAR SESION",
                onClick = { viewModel.onEvent(LoginEvent.Login) },
                enabled = !state.isLoading,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            if (state.isLoading) {
                Spacer(modifier = Modifier.height(20.dp))
                CircularProgressIndicator(
                    color = BrightRed,
                    modifier = Modifier.size(40.dp)
                )
            }

            state.error?.let {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = it, color = Color.Red, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically,) {
                Text(
                    text = "¿No tienes cuenta?",
                    color = White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = onNavigateToRegister) {
                    Text(
                        text = "Regístrate",
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
