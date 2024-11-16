package com.example.walletcards.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.walletcards.R

@Composable
fun WelcomeScreen(onLoginClicked: () -> Unit, onRegisterClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Título y Descripción (20% del espacio)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f), // Ocupa el 20% del espacio
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "👋Hey!",
                fontSize = 46.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Obtén tu Tarjeta de Presentación Digital aquí!",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        // Imagen (65% del espacio)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.75f) // Ahora ocupa el 65% del espacio
                .padding(horizontal = 0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_image), // Cambia "your_image" por tu imagen
                contentDescription = "Imagen principal",
                modifier = Modifier
                    .fillMaxHeight() // Usa el espacio completo del 65%
                    .aspectRatio(0.8f) // Ajusta la proporción para que sea más grande
                    .align(Alignment.CenterStart) // Alinea completamente a la izquierda
                    .padding(start = 0.dp) // Elimina cualquier padding adicional
            )
        }

        // Botones (15% del espacio)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.15f), // Ocupa solo el 15% del espacio
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaciado reducido entre botones
        ) {
            Button(
                onClick = onLoginClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF631C7F), // Color de fondo del botón
                    contentColor = Color.White // Color del texto del botón
                ),
                modifier = Modifier.fillMaxWidth(0.85f) // Botones grandes, 85% del ancho
            ) {
                Text("Iniciar sesión")
            }
            Button(
                onClick = onRegisterClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF631C7F), // Color de fondo del botón
                    contentColor = Color.White // Color del texto del botón
                ),
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Registrarse")
            }
        }
    }
}
