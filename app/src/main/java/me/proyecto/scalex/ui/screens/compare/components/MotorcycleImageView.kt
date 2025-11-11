package me.proyecto.scalex.ui.screens.compare.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.proyecto.scalex.R
import me.proyecto.scalex.data.model.Motorcycle

@Composable
fun MotorcycleSideComparison(
    motorcycle1: Motorcycle?,
    motorcycle2: Motorcycle?,
    color1: Color,
    color2: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Moto 1
            if (motorcycle1 != null) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.motorcycle_side),
                        contentDescription = "${motorcycle1.make} ${motorcycle1.model}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(color1)
                    )
                }
            }

            // VS
            Text(
                text = "VS",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Moto 2
            if (motorcycle2 != null) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.motorcycle_side),
                        contentDescription = "${motorcycle2.make} ${motorcycle2.model}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(color2)
                    )
                }
            }
        }
    }
}

@Composable
fun MotorcycleTopComparison(
    motorcycle1: Motorcycle?,
    motorcycle2: Motorcycle?,
    color1: Color,
    color2: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Moto 1
            if (motorcycle1 != null) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.motorcycle_top),
                        contentDescription = "${motorcycle1.make} ${motorcycle1.model} - Vista superior",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(color1)
                    )
                }
            }

            // Separador
            if (motorcycle1 != null && motorcycle2 != null) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(60.dp)
                        .background(Color.White.copy(alpha = 0.3f))
                )
            }

            // Moto 2
            if (motorcycle2 != null) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.motorcycle_top),
                        contentDescription = "${motorcycle2.make} ${motorcycle2.model} - Vista superior",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(color2)
                    )
                }
            }
        }
    }
}

@Composable
fun MotorcycleThumbnail(
    motorcycle: Motorcycle?,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (motorcycle != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.motorcycle_side),
                    contentDescription = motorcycle.getFullName(),
                    modifier = Modifier
                        .size(60.dp),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = motorcycle.make,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Text(
                text = "[Sin moto]",
                color = Color.White.copy(alpha = 0.3f),
                fontSize = 10.sp
            )
        }
    }
}