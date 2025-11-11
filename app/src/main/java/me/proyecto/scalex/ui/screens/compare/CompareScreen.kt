package me.proyecto.scalex.ui.screens.compare

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import me.proyecto.scalex.R
import me.proyecto.scalex.ui.screens.compare.components.MotorcycleCard
import me.proyecto.scalex.ui.screens.compare.components.MotorcycleSelector
import me.proyecto.scalex.ui.screens.compare.components.TechnicalSpecsCard
import me.proyecto.scalex.ui.theme.BrightRed
import me.proyecto.scalex.ui.theme.DarkBrown
import me.proyecto.scalex.ui.theme.White

@Composable
fun CompareScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    val viewModel: CompareViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                CompareViewModel(context.applicationContext as android.app.Application)
            }
        }
    )

    val state by viewModel.state.collectAsState()

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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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

                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo_scalex),
                    contentDescription = "ScaleX Logo",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(80.dp)
                        .width(200.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Título "PRUEBA LA MOTOCICLETA A COMPARAR"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBrown)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "PRUEBA LA MOTOCICLETA A COMPARAR",
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Espacio para visualización de tamaños - Vista LATERAL (PLACEHOLDER)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.DarkGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .border(1.dp, White.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "VISTA LATERAL",
                        color = White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "[Comparación visual de tamaños]",
                        color = White.copy(alpha = 0.4f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (state.motorcycle1 != null && state.motorcycle2 != null) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.motorcycle1!!.make,
                                color = BrightRed,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = " VS ",
                                color = White.copy(alpha = 0.5f),
                                fontSize = 11.sp
                            )
                            Text(
                                text = state.motorcycle2!!.make,
                                color = Color.Cyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Vista SUPERIOR (PLACEHOLDER)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.DarkGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .border(1.dp, White.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "VISTA SUPERIOR",
                        color = White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "[Comparación visual de tamaños]",
                        color = White.copy(alpha = 0.4f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cards con información de las motos
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Moto 1 (Rojo)
                MotorcycleCard(
                    motorcycle = state.motorcycle1,
                    color = BrightRed,
                    colorName = "Rojo",
                    isFavorite = state.motorcycle1?.let {
                        it.getId() in state.favorites
                    } ?: false,
                    onRemove = { viewModel.onEvent(CompareEvent.RemoveMotorcycle1) },
                    onToggleFavorite = {
                        state.motorcycle1?.let {
                            viewModel.onEvent(CompareEvent.ToggleFavorite(it.getId(), it))
                        }
                    },
                    onShowSearch = { viewModel.onEvent(CompareEvent.ShowSelector1) },
                    modifier = Modifier.weight(1f)
                )

                // Moto 2 (Cyan)
                MotorcycleCard(
                    motorcycle = state.motorcycle2,
                    color = Color.Cyan,
                    colorName = "Azul",
                    isFavorite = state.motorcycle2?.let {
                        it.getId() in state.favorites
                    } ?: false,
                    onRemove = { viewModel.onEvent(CompareEvent.RemoveMotorcycle2) },
                    onToggleFavorite = {
                        state.motorcycle2?.let {
                            viewModel.onEvent(CompareEvent.ToggleFavorite(it.getId(), it))
                        }
                    },
                    onShowSearch = { viewModel.onEvent(CompareEvent.ShowSelector2) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Thumbnails de motos (PLACEHOLDER)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Thumbnail 1
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .background(Color.DarkGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .border(2.dp, BrightRed, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🏍️",
                            fontSize = 32.sp
                        )
                        if (state.motorcycle1 != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = state.motorcycle1!!.make,
                                color = White.copy(alpha = 0.6f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Text(
                                text = "[Sin moto]",
                                color = White.copy(alpha = 0.3f),
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Thumbnail 2
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .background(Color.DarkGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Cyan, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🏍️",
                            fontSize = 32.sp
                        )
                        if (state.motorcycle2 != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = state.motorcycle2!!.make,
                                color = White.copy(alpha = 0.6f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Text(
                                text = "[Sin moto]",
                                color = White.copy(alpha = 0.3f),
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tabla de especificaciones técnicas
            if (state.motorcycle1 != null || state.motorcycle2 != null) {
                TechnicalSpecsCard(
                    motorcycle1 = state.motorcycle1,
                    motorcycle2 = state.motorcycle2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            } else {
                // Mensaje cuando no hay motos seleccionadas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🏍️",
                            fontSize = 64.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Agrega motocicletas para comparar",
                            color = White.copy(alpha = 0.7f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Presiona '+' para buscar y seleccionar motos",
                            color = White.copy(alpha = 0.5f),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Loading indicator
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = DarkBrown
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = BrightRed)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Buscando motocicletas...",
                            color = White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Diálogo de búsqueda para Moto 1
        if (state.showSelector1) {
            MotorcycleSelector(
                searchQuery = state.searchQuery,
                searchResults = state.searchResults,
                isLoading = state.isLoading,
                error = state.error,
                onQueryChange = { viewModel.onEvent(CompareEvent.UpdateSearchQuery(it)) },
                onSearch = { viewModel.onEvent(CompareEvent.SearchMotorcycles(it)) },
                onSelect = { viewModel.onEvent(CompareEvent.SelectMotorcycle1(it)) },
                onDismiss = { viewModel.onEvent(CompareEvent.HideSelector1) }
            )
        }

        // Diálogo de búsqueda para Moto 2
        if (state.showSelector2) {
            MotorcycleSelector(
                searchQuery = state.searchQuery,
                searchResults = state.searchResults,
                isLoading = state.isLoading,
                error = state.error,
                onQueryChange = { viewModel.onEvent(CompareEvent.UpdateSearchQuery(it)) },
                onSearch = { viewModel.onEvent(CompareEvent.SearchMotorcycles(it)) },
                onSelect = { viewModel.onEvent(CompareEvent.SelectMotorcycle2(it)) },
                onDismiss = { viewModel.onEvent(CompareEvent.HideSelector2) }
            )
        }
    }
}