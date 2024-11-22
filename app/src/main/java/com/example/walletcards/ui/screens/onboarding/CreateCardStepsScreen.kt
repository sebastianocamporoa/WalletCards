package com.example.walletcards.ui.screens.onboarding

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.walletcards.data.repository.BusinessCard
import com.example.walletcards.data.repository.FirestoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCardStepsScreen(onComplete: () -> Unit) {
    val repository = FirestoreRepository() // Usamos el repositorio de Firestore

    var step by remember { mutableStateOf(1) }
    var name by rememberSaveable { mutableStateOf("") }
    var company by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var photoUri by rememberSaveable { mutableStateOf<Uri?>(null) } // Almacenar la URI seleccionada
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Configurar el lanzador para abrir la galería
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        photoUri = uri // Almacenar la URI seleccionada
    }

    val purpleColor = Color(0xFF631C7F)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (step) {
            1 -> {
                Text("¡Estamos encantados de darte la bienvenida! Comencemos creando tu primera tarjeta de presentación digital")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { step++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purpleColor,
                        contentColor = Color.White
                    ),
                ) {
                    Text("Crea mi primer tarjeta")
                }
            }
            2 -> {
                Text("Ingrese su nombre completo")
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre completo") },
                    textStyle = TextStyle(color = purpleColor),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = purpleColor,
                        unfocusedBorderColor = purpleColor.copy(alpha = 0.6f),
                        cursorColor = purpleColor,
                        focusedLabelColor = purpleColor,
                        unfocusedLabelColor = purpleColor.copy(alpha = 0.6f)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { step++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purpleColor,
                        contentColor = Color.White
                    ),
                ) {
                    Text("Siguiente")
                }
            }
            3 -> {
                Text("Ingrese su empresa y título")
                OutlinedTextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text("Empresa") },
                    textStyle = TextStyle(color = purpleColor),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = purpleColor,
                        unfocusedBorderColor = purpleColor.copy(alpha = 0.6f),
                        cursorColor = purpleColor,
                        focusedLabelColor = purpleColor,
                        unfocusedLabelColor = purpleColor.copy(alpha = 0.6f)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    textStyle = TextStyle(color = purpleColor),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = purpleColor,
                        unfocusedBorderColor = purpleColor.copy(alpha = 0.6f),
                        cursorColor = purpleColor,
                        focusedLabelColor = purpleColor,
                        unfocusedLabelColor = purpleColor.copy(alpha = 0.6f)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { step++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purpleColor,
                        contentColor = Color.White
                    ),
                ) {
                    Text("Siguiente")
                }
            }
            4 -> {
                Text("Cargar una foto")
                Spacer(modifier = Modifier.height(8.dp))

                // Botón para seleccionar imagen
                Button(
                    onClick = {
                        galleryLauncher.launch("image/*") // Abrir la galería para seleccionar una imagen
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purpleColor,
                        contentColor = Color.White
                    ),
                ) {
                    Text("Seleccionar imagen")
                }

                // Mostrar la imagen seleccionada
                photoUri?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Foto seleccionada",
                        modifier = Modifier.size(150.dp) // Imagen más grande
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { step++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purpleColor,
                        contentColor = Color.White
                    ),
                ) {
                    Text("Siguiente")
                }
            }
            5 -> {
                Text("Ingrese su número de teléfono")
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Número de teléfono") },
                    textStyle = TextStyle(color = purpleColor),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = purpleColor,
                        unfocusedBorderColor = purpleColor.copy(alpha = 0.6f),
                        cursorColor = purpleColor,
                        focusedLabelColor = purpleColor,
                        unfocusedLabelColor = purpleColor.copy(alpha = 0.6f)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = androidx.compose.ui.graphics.Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (name.isNotBlank() && company.isNotBlank() && phoneNumber.isNotBlank()) {
                            isLoading = true
                            errorMessage = null

                            // Guardar la tarjeta en Firestore
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    val newCard = BusinessCard(
                                        name = name,
                                        company = company,
                                        position = title,
                                        phone = phoneNumber,
                                        photoUri = photoUri?.toString() // Guardamos la URI como String
                                    )
                                    repository.createCard(newCard) // Guardar la tarjeta en Firestore
                                    onComplete() // Redirigir a la pantalla principal
                                } catch (e: Exception) {
                                    errorMessage = "Error: ${e.message}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        } else {
                            errorMessage = "Por favor, complete todos los campos."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = purpleColor,
                        contentColor = Color.White
                    ),
                ) {
                    if (isLoading) {
                        androidx.compose.material3.CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = androidx.compose.ui.graphics.Color.White
                        )
                    } else {
                        Text("Finalizar")
                    }
                }
            }
        }
    }
}
