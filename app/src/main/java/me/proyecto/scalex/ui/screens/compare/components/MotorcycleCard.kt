package me.proyecto.scalex.ui.screens.compare.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.proyecto.scalex.data.model.Motorcycle
import me.proyecto.scalex.ui.theme.BrightRed
import me.proyecto.scalex.ui.theme.DarkBrown
import me.proyecto.scalex.ui.theme.White

@Composable
fun MotorcycleCard(
    motorcycle: Motorcycle?,
    color: Color,
    colorName: String,
    isFavorite: Boolean,
    onRemove: () -> Unit,
    onToggleFavorite: () -> Unit,
    onShowSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .border(2.dp, White, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = DarkBrown
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (motorcycle != null) {
            // Mostrar información de la moto
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Color indicator
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(color, RoundedCornerShape(4.dp))
                                .border(1.dp, White, RoundedCornerShape(4.dp))
                        )
                        Text(
                            text = colorName.uppercase(),
                            color = White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Nombre de la moto
                    Text(
                        text = motorcycle.getFullName(),
                        color = White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2
                    )

                    // Información básica
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "MOTOR",
                            color = White.copy(alpha = 0.7f),
                            fontSize = 9.sp
                        )
                        Text(
                            text = motorcycle.displacement?.substringBefore("ccm")?.trim() ?: "N/A",
                            color = White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Botones de acción (esquina superior derecha)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Botón de favorito
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite) BrightRed else White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Botón de eliminar
                    IconButton(
                        onClick = onRemove,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Eliminar",
                            tint = White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        } else {
            // Mostrar botón de agregar
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = onShowSearch,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar Vehículo",
                            tint = White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "+ AGREGAR VEHÍCULO",
                        color = White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}