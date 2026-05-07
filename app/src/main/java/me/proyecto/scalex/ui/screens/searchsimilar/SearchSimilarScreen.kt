package me.proyecto.scalex.ui.screens.searchsimilar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.proyecto.scalex.R
import me.proyecto.scalex.domain.model.Motorcycle
import me.proyecto.scalex.ui.screens.compare.components.MotorcycleSelector
import me.proyecto.scalex.ui.theme.BrightRed
import me.proyecto.scalex.ui.theme.DarkBrown
import me.proyecto.scalex.ui.theme.White

@Composable
fun SearchSimilarScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCompare: (Motorcycle) -> Unit = {},
    viewModel: SearchSimilarViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val state = when (val s = uiState) {
        is SearchSimilarUiState.Idle -> SearchSimilarUiState.Success()
        is SearchSimilarUiState.Loading -> SearchSimilarUiState.Success(isLoading = true)
        is SearchSimilarUiState.Success -> s
        is SearchSimilarUiState.Error -> SearchSimilarUiState.Success(error = s.message)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBrown)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (state.selectedMotorcycle != null)
                        "VEHÍCULOS SIMILARES A ${state.selectedMotorcycle!!.make.uppercase()} ${state.selectedMotorcycle!!.model.uppercase()}"
                    else
                        "BUSCAR SIMILARES",
                    color = White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.selectedMotorcycle == null) {
                Button(
                    onClick = { viewModel.onEvent(SearchSimilarEvent.ShowSelector) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrightRed
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "SELECCIONAR MOTOCICLETA",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .border(2.dp, White, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkBrown
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Moto Base:",
                                color = White.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                            Text(
                                text = state.selectedMotorcycle!!.getFullName(),
                                color = White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = state.selectedMotorcycle!!.displacement ?: "N/A",
                                color = White.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                        }

                        TextButton(
                            onClick = { viewModel.onEvent(SearchSimilarEvent.ClearSelection) }
                        ) {
                            Text("Cambiar", color = BrightRed)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = BrightRed)
                    }
                }
                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error!!,
                            color = Color.Red,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                state.similarMotorcycles.isEmpty() && state.selectedMotorcycle != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "\uD83D\uDD0D",
                                fontSize = 64.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Buscando motos similares...",
                                color = White.copy(alpha = 0.7f),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                state.similarMotorcycles.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                text = "MISMA FORMA",
                                color = Color.Cyan,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(state.similarMotorcycles.take(3)) { motorcycle ->
                            SimilarMotorcycleCard(
                                motorcycle = motorcycle,
                                category = "MISMA FORMA",
                                onClick = { onNavigateToCompare(motorcycle) }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "FORMA SIMILAR",
                                color = Color(0xFFFFD700),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(state.similarMotorcycles.drop(3).take(5)) { motorcycle ->
                            SimilarMotorcycleCard(
                                motorcycle = motorcycle,
                                category = "FORMA SIMILAR",
                                onClick = { onNavigateToCompare(motorcycle) }
                            )
                        }
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "\uD83C\uDFCD",
                                fontSize = 64.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Selecciona una motocicleta para\nencontrar similares",
                                color = White.copy(alpha = 0.7f),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        if (state.showSelector) {
            MotorcycleSelector(
                searchQuery = state.searchQuery,
                searchResults = state.searchResults,
                isLoading = state.isLoading,
                error = state.error,
                onQueryChange = { viewModel.onEvent(SearchSimilarEvent.UpdateSearchQuery(it)) },
                onSearch = { viewModel.onEvent(SearchSimilarEvent.SearchMotorcycles(it)) },
                onSelect = { viewModel.onEvent(SearchSimilarEvent.SelectMotorcycle(it)) },
                onDismiss = { viewModel.onEvent(SearchSimilarEvent.HideSelector) }
            )
        }
    }
}

@Composable
fun SimilarMotorcycleCard(
    motorcycle: Motorcycle,
    category: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, White.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.DarkGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "\uD83C\uDFCD", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = motorcycle.make.uppercase(),
                    color = if (category == "MISMA FORMA") Color.Cyan else Color(0xFFFFD700),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = motorcycle.model.trim(),
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = motorcycle.year,
                    color = White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = motorcycle.displacement?.substringBefore("ccm")?.trim() ?: "N/A",
                    color = White.copy(alpha = 0.6f),
                    fontSize = 11.sp
                )
            }
        }
    }
}
