package me.proyecto.scalex.ui.screens.compare.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.proyecto.scalex.data.model.Motorcycle
import me.proyecto.scalex.ui.theme.DarkBrown
import me.proyecto.scalex.ui.theme.White

@Composable
fun TechnicalSpecsCard(
    motorcycle1: Motorcycle?,
    motorcycle2: Motorcycle?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        // Título
        Text(
            text = "ESPECIFICACIONES TÉCNICAS",
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Tabla
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = DarkBrown.copy(alpha = 0.8f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Header de la tabla
                SpecHeaderRow()

                Divider(color = White.copy(alpha = 0.3f), thickness = 2.dp)

                // Categoría: MOTOR
                SpecCategoryHeader("MOTOR")

                SpecRow(
                    label = "Tipo",
                    value1 = motorcycle1?.engine,
                    value2 = motorcycle2?.engine
                )

                SpecRow(
                    label = "Cilindrada",
                    value1 = motorcycle1?.displacement,
                    value2 = motorcycle2?.displacement
                )

                SpecRow(
                    label = "Potencia",
                    value1 = motorcycle1?.power,
                    value2 = motorcycle2?.power
                )

                SpecRow(
                    label = "Torque",
                    value1 = motorcycle1?.torque,
                    value2 = motorcycle2?.torque
                )

                SpecRow(
                    label = "Refrigeración",
                    value1 = motorcycle1?.cooling,
                    value2 = motorcycle2?.cooling
                )

                SpecRow(
                    label = "Compresión",
                    value1 = motorcycle1?.compression,
                    value2 = motorcycle2?.compression
                )

                Divider(color = White.copy(alpha = 0.3f), thickness = 1.dp)

                // Categoría: TRANSMISIÓN
                SpecCategoryHeader("TRANSMISIÓN")

                SpecRow(
                    label = "Caja de cambios",
                    value1 = motorcycle1?.gearbox,
                    value2 = motorcycle2?.gearbox
                )

                SpecRow(
                    label = "Transmisión",
                    value1 = motorcycle1?.transmission,
                    value2 = motorcycle2?.transmission
                )

                SpecRow(
                    label = "Embrague",
                    value1 = motorcycle1?.clutch,
                    value2 = motorcycle2?.clutch
                )

                Divider(color = White.copy(alpha = 0.3f), thickness = 1.dp)

                // Categoría: DIMENSIONES Y PESO
                SpecCategoryHeader("DIMENSIONES Y PESO")

                SpecRow(
                    label = "Peso total",
                    value1 = motorcycle1?.totalWeight,
                    value2 = motorcycle2?.totalWeight
                )

                SpecRow(
                    label = "Altura total",
                    value1 = motorcycle1?.totalHeight,
                    value2 = motorcycle2?.totalHeight
                )

                SpecRow(
                    label = "Longitud total",
                    value1 = motorcycle1?.totalLength,
                    value2 = motorcycle2?.totalLength
                )

                SpecRow(
                    label = "Ancho total",
                    value1 = motorcycle1?.totalWidth,
                    value2 = motorcycle2?.totalWidth
                )

                SpecRow(
                    label = "Altura del asiento",
                    value1 = motorcycle1?.seatHeight,
                    value2 = motorcycle2?.seatHeight
                )

                SpecRow(
                    label = "Distancia entre ejes",
                    value1 = motorcycle1?.wheelbase,
                    value2 = motorcycle2?.wheelbase
                )

                SpecRow(
                    label = "Despeje del suelo",
                    value1 = motorcycle1?.groundClearance,
                    value2 = motorcycle2?.groundClearance
                )

                Divider(color = White.copy(alpha = 0.3f), thickness = 1.dp)

                // Categoría: FRENOS Y SUSPENSIÓN
                SpecCategoryHeader("FRENOS Y SUSPENSIÓN")

                SpecRow(
                    label = "Frenos delanteros",
                    value1 = motorcycle1?.frontBrakes,
                    value2 = motorcycle2?.frontBrakes
                )

                SpecRow(
                    label = "Frenos traseros",
                    value1 = motorcycle1?.rearBrakes,
                    value2 = motorcycle2?.rearBrakes
                )

                SpecRow(
                    label = "Suspensión delantera",
                    value1 = motorcycle1?.frontSuspension,
                    value2 = motorcycle2?.frontSuspension
                )

                SpecRow(
                    label = "Suspensión trasera",
                    value1 = motorcycle1?.rearSuspension,
                    value2 = motorcycle2?.rearSuspension
                )

                Divider(color = White.copy(alpha = 0.3f), thickness = 1.dp)

                // Categoría: COMBUSTIBLE
                SpecCategoryHeader("COMBUSTIBLE")

                SpecRow(
                    label = "Capacidad",
                    value1 = motorcycle1?.fuelCapacity,
                    value2 = motorcycle2?.fuelCapacity
                )

                SpecRow(
                    label = "Sistema",
                    value1 = motorcycle1?.fuelSystem,
                    value2 = motorcycle2?.fuelSystem
                )
            }
        }
    }
}

@Composable
fun SpecHeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "ESPECIFICACIÓN",
            color = White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "MOTO 1",
            color = White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "MOTO 2",
            color = White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SpecCategoryHeader(category: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.3f))
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Text(
            text = category,
            color = White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SpecRow(
    label: String,
    value1: String?,
    value2: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Etiqueta
        Text(
            text = label,
            color = White.copy(alpha = 0.9f),
            fontSize = 10.sp,
            modifier = Modifier.weight(1f)
        )

        // Valor 1 (Moto 1)
        Text(
            text = value1?.take(40) ?: "-",
            color = White.copy(alpha = 0.8f),
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
            maxLines = 3
        )

        // Valor 2 (Moto 2)
        Text(
            text = value2?.take(40) ?: "-",
            color = White.copy(alpha = 0.8f),
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
            maxLines = 3
        )
    }
    Divider(color = White.copy(alpha = 0.1f), thickness = 1.dp)
}