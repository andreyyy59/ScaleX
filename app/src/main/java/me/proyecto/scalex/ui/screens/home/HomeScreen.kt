package me.proyecto.scalex.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.proyecto.scalex.ui.theme.BrightRed
import me.proyecto.scalex.ui.theme.White
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(
    onLogout: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "✅ Login Exitoso!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Bienvenido a ScaleX",
                fontSize = 20.sp,
                color = White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onLogout,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = BrightRed
                )
            ) {
                Text("Cerrar Sesión", color = White)
            }
        }
    }
}