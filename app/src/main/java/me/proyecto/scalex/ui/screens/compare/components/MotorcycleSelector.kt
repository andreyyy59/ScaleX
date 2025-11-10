package me.proyecto.scalex.ui.screens.compare.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.proyecto.scalex.data.model.Motorcycle
import me.proyecto.scalex.ui.theme.BrightRed
import me.proyecto.scalex.ui.theme.DarkBrown
import me.proyecto.scalex.ui.theme.White

@Composable
fun MotorcycleSelector(
    searchQuery: String,
    searchResults: List<Motorcycle>,
    isLoading: Boolean,
    error: String?,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onSelect: (Motorcycle) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkBrown,
        title = {
            Text(
                text = "Buscar Motocicleta",
                fontWeight = FontWeight.Bold,
                color = White
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Campo de búsqueda
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    label = { Text("Modelo", color = White.copy(alpha = 0.7f)) },
                    placeholder = { Text("Ej: Ninja, R1, CBR...", color = White.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        focusedBorderColor = BrightRed,
                        unfocusedBorderColor = White.copy(alpha = 0.5f),
                        cursorColor = BrightRed
                    ),
                    trailingIcon = {
                        IconButton(onClick = { onSearch(searchQuery) }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = BrightRed
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contenido según estado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                ) {
                    when {
                        isLoading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = BrightRed)
                            }
                        }
                        error != null -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = error,
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        searchResults.isEmpty() -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Escribe un modelo y presiona buscar",
                                    color = White.copy(alpha = 0.5f),
                                    textAlign = TextAlign.Center,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        else -> {
                            // Lista de resultados
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                searchResults.forEach { motorcycle ->
                                    MotorcycleResultItem(
                                        motorcycle = motorcycle,
                                        onClick = { onSelect(motorcycle) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = White)
            }
        }
    )
}

@Composable
fun MotorcycleResultItem(
    motorcycle: Motorcycle,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2D1810)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = motorcycle.getFullName(),
                fontWeight = FontWeight.Bold,
                color = White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tipo: ${motorcycle.type ?: "N/A"}",
                    fontSize = 12.sp,
                    color = White.copy(alpha = 0.7f)
                )
                Text(
                    text = motorcycle.displacement?.substringBefore("ccm")?.trim() ?: "N/A",
                    fontSize = 12.sp,
                    color = White.copy(alpha = 0.7f)
                )
            }
            if (motorcycle.power != null) {
                Text(
                    text = "Potencia: ${motorcycle.power}",
                    fontSize = 11.sp,
                    color = White.copy(alpha = 0.6f)
                )
            }
        }
    }
}