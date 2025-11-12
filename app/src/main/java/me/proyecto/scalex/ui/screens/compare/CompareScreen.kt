// ui/screens/compare/CompareScreen.kt

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
import me.proyecto.scalex.data.model.Motorcycle
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

            // Visualización de comparación de tamaños
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.Black, RoundedCornerShape(12.dp))
                    .border(2.dp, White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Moto 1
                    if (state.motorcycle1 != null) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Etiqueta marca
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(BrightRed.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .border(1.dp, BrightRed, RoundedCornerShape(4.dp))
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = state.motorcycle1!!.make.uppercase(),
                                    color = BrightRed,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Imagen de la moto
                            Image(
                                painter = painterResource(id = getMotorcycleImageResource(state.motorcycle1!!)),
                                contentDescription = state.motorcycle1!!.getFullName(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Nombre modelo
                            Text(
                                text = state.motorcycle1!!.model.trim(),
                                color = White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                maxLines = 2
                            )

                            // Año
                            Text(
                                text = state.motorcycle1!!.year,
                                color = White.copy(alpha = 0.7f),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // Cilindrada
                            Text(
                                text = state.motorcycle1!!.displacement?.substringBefore("ccm")?.trim() ?: "N/A",
                                color = White.copy(alpha = 0.6f),
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "MOTO 1",
                                    color = White.copy(alpha = 0.3f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "[Sin seleccionar]",
                                    color = White.copy(alpha = 0.3f),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    // Divisor VS
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(160.dp)
                                .background(White.copy(alpha = 0.3f))
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "VS",
                            color = White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(160.dp)
                                .background(White.copy(alpha = 0.3f))
                        )
                    }

                    // Moto 2
                    if (state.motorcycle2 != null) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Etiqueta marca
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Cyan.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .border(1.dp, Color.Cyan, RoundedCornerShape(4.dp))
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = state.motorcycle2!!.make.uppercase(),
                                    color = Color.Cyan,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Imagen de la moto
                            Image(
                                painter = painterResource(id = getMotorcycleImageResource(state.motorcycle2!!)),
                                contentDescription = state.motorcycle2!!.getFullName(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentScale = ContentScale.Fit
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Nombre modelo
                            Text(
                                text = state.motorcycle2!!.model.trim(),
                                color = White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                maxLines = 2
                            )

                            // Año
                            Text(
                                text = state.motorcycle2!!.year,
                                color = White.copy(alpha = 0.7f),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // Cilindrada
                            Text(
                                text = state.motorcycle2!!.displacement?.substringBefore("ccm")?.trim() ?: "N/A",
                                color = White.copy(alpha = 0.6f),
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "MOTO 2",
                                    color = White.copy(alpha = 0.3f),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "[Sin seleccionar]",
                                    color = White.copy(alpha = 0.3f),
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
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

// Función para mapear imágenes de motocicletas
private fun getMotorcycleImageResource(motorcycle: Motorcycle): Int {
    val normalizedModel = motorcycle.model.trim().lowercase()
        .replace(" ", "_")
        .replace("-", "_")

    val normalizedMake = motorcycle.make.trim().lowercase()

    return when {
        // Suzuki
        normalizedModel.contains("gixxer") && normalizedModel.contains("250") && normalizedModel.contains("sf") -> R.drawable.gixxer_250_sf
        normalizedModel.contains("gn") && normalizedModel.contains("125") -> R.drawable.suzuki_gn_125
        normalizedModel.contains("access") && normalizedModel.contains("125") -> R.drawable.access_125
        normalizedModel.contains("boulevard") && normalizedModel.contains("c50") -> R.drawable.boulevard_c50

        // Kawasaki
        normalizedModel.contains("concours") && normalizedModel.contains("14") -> R.drawable.concours_14
        normalizedModel.contains("klr") && normalizedModel.contains("650") -> R.drawable.klr_650
        normalizedModel.contains("klx") && normalizedModel.contains("110") -> R.drawable.klx_110r

        // TVS
        normalizedModel.contains("apache") && normalizedModel.contains("rr310") -> R.drawable.apache_rr310
        normalizedModel.contains("apache") && normalizedModel.contains("rtr") && normalizedModel.contains("160") -> R.drawable.apache_rtr_160_2022
        normalizedModel.contains("raider") && normalizedModel.contains("125") -> R.drawable.raider_125

        // Hero
        normalizedModel.contains("hunk") && normalizedModel.contains("150") -> R.drawable.hero_hunk_150
        normalizedModel.contains("ct") && normalizedModel.contains("100") -> R.drawable.ct_100

        // Bajaj
        normalizedModel.contains("dominar") && normalizedModel.contains("250") -> R.drawable.dominar_250
        normalizedModel.contains("dominar") && normalizedModel.contains("400") -> R.drawable.dominar_400
        normalizedModel.contains("pulsar") && normalizedModel.contains("220") -> R.drawable.pulsar_220f
        normalizedModel.contains("pulsar") && normalizedModel.contains("150") -> R.drawable.pulsar_150

        // Honda
        normalizedModel.contains("adv350") || normalizedModel.contains("adv_350") -> R.drawable.adv350
        normalizedModel.contains("africa") && normalizedModel.contains("twin") -> R.drawable.africa_twin
        normalizedModel.contains("cb1000r") || normalizedModel.contains("cb_1000_r") -> R.drawable.cb1000r
        normalizedModel.contains("cb125f") || normalizedModel.contains("cb_125_f") -> R.drawable.cb125f
        normalizedModel.contains("cb150f") || normalizedModel.contains("cb_150_f") -> R.drawable.cb150f_2022
        normalizedModel.contains("cb200x") || normalizedModel.contains("cb_200_x") -> R.drawable.cb200x_2022

        // BMW
        normalizedModel.contains("c_400_gt") || normalizedModel.contains("c400gt") -> R.drawable.c_400_gt
        normalizedModel.contains("f_750_gs") || normalizedModel.contains("f750gs") -> R.drawable.f_750_gs_2022
        normalizedModel.contains("g_310_r") || normalizedModel.contains("g310r") -> R.drawable.g_310_r_2022
        normalizedModel.contains("k_1600_b") || normalizedModel.contains("k1600b") -> R.drawable.k_1600_b
        normalizedModel.contains("m_1000_rr") || normalizedModel.contains("m1000rr") -> R.drawable.m_1000_rr


        // Placeholder por defecto
        else -> R.drawable.ct_100
    }
}