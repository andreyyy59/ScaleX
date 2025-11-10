package me.proyecto.scalex

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.proyecto.scalex.R
import me.proyecto.scalex.data.model.Motorcycle
import me.proyecto.scalex.ui.theme.BrightRed
import me.proyecto.scalex.ui.theme.DarkBrown
import me.proyecto.scalex.ui.theme.White

@Composable
fun FavoritesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCompare: (Motorcycle?) -> Unit = {}
) {
    val viewModel: FavoritesViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
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
                            Color.Black.copy(alpha = 0.85f),
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header con logo y botón de volver
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Botón de volver
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Título
                Text(
                    text = "MIS FAVORITOS",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Contador de favoritos
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBrown)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${state.favorites.size} motocicletas guardadas",
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de favoritos
            if (state.favorites.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.favorites) { motorcycle ->
                        FavoriteMotorcycleItem(
                            motorcycle = motorcycle,
                            onRemoveFromFavorites = {
                                viewModel.removeFromFavorites(motorcycle.getId())
                            },
                            onUseInCompare = {
                                onNavigateToCompare(motorcycle)
                            }
                        )
                    }
                }
            } else {
                // Estado vacío
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Sin favoritos",
                            tint = White.copy(alpha = 0.5f),
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Aún no tienes favoritos",
                            color = White.copy(alpha = 0.7f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Agrega motocicletas a favoritos desde la pantalla de comparación",
                            color = White.copy(alpha = 0.5f),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Loading indicator
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = BrightRed)
            }
        }
    }
}

@Composable
fun FavoriteMotorcycleItem(
    motorcycle: Motorcycle,
    onRemoveFromFavorites: () -> Unit,
    onUseInCompare: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkBrown
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Información de la moto
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = motorcycle.getFullName(),
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = motorcycle.type ?: "Tipo no especificado",
                            color = White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }

                    // Especificaciones principales
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column {
                            Text(
                                text = "CILINDRADA",
                                color = White.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                            Text(
                                text = motorcycle.displacement?.substringBefore("ccm")?.trim() ?: "N/A",
                                color = White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text(
                                text = "POTENCIA",
                                color = White.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                            Text(
                                text = motorcycle.power?.substringBefore("HP")?.trim() ?: "N/A",
                                color = White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Botones de acción
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Botón usar en comparación
                    Button(
                        onClick = onUseInCompare,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrightRed,
                            contentColor = White
                        ),
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text(
                            text = "USAR EN COMPARACIÓN",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Botón quitar de favoritos
                    TextButton(
                        onClick = onRemoveFromFavorites,
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text(
                            text = "QUITAR DE FAVORITOS",
                            fontSize = 10.sp,
                            color = White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}