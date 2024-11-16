package com.example.walletcards.ui.screens.onboarding

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.walletcards.data.model.BusinessCard
import com.example.walletcards.data.repository.BusinessCardRepository

@Composable
fun CreateCardStepsScreen(repository: BusinessCardRepository = BusinessCardRepository(), onComplete: () -> Unit) {
    var step by remember { mutableStateOf(1) }
    var name by rememberSaveable { mutableStateOf("") }
    var company by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var photoUri by rememberSaveable { mutableStateOf<Uri?>(null) } // Usar URI para la imagen seleccionada

    // Configurar el lanzador para abrir la galería
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        photoUri = uri // Almacenar la URI seleccionada
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (step) {
            1 -> {
                Text("Bienvenido a WalletCards")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { step++ }) {
                    Text("Crea mi primer tarjeta")
                }
            }
            2 -> {
                Text("Ingrese su nombre completo")
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre completo") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { step++ }) {
                    Text("Siguiente")
                }
            }
            3 -> {
                Text("Ingrese su empresa y título")
                OutlinedTextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text("Empresa") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { step++ }) {
                    Text("Siguiente")
                }
            }
            4 -> {
                Text("Cargar una foto")
                Spacer(modifier = Modifier.height(8.dp))

                // Botón para seleccionar imagen
                Button(onClick = {
                    galleryLauncher.launch("image/*") // Abrir la galería para seleccionar una imagen
                }) {
                    Text("Seleccionar imagen")
                }

                // Mostrar la imagen seleccionada
                photoUri?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Foto seleccionada",
                        modifier = Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { step++ }) {
                    Text("Siguiente")
                }
            }
            5 -> {
                Text("Ingrese su número de teléfono")
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Número de teléfono") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val newCard = BusinessCard(
                        id = repository.getCards().size + 1,
                        name = name,
                        company = company,
                        position = title,
                        phone = phoneNumber,
                        email = "", // Agrega el email si es necesario
                        photoUri = photoUri // Guarda la URI de la foto seleccionada
                    )
                    repository.addCard(newCard)
                    onComplete() // Llamamos a onComplete para redirigir a MainScreen
                }) {
                    Text("Finalizar")
                }
            }
        }
    }
}
