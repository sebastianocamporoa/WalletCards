package com.example.walletcards.ui.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val purpleColor = Color(0xFF631C7F)
    val coroutineScope = rememberCoroutineScope() // Corutina para operaciones asíncronas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Registrarse", style = TextStyle(color = purpleColor, fontSize = 24.sp))

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
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

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            textStyle = TextStyle(color = purpleColor),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = description)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

        // Campo para confirmar contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            textStyle = TextStyle(color = purpleColor),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (confirmPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = icon, contentDescription = description)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

        // Mostrar errores
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Botón de registro
        Button(
            onClick = {
                if (email.isNotEmpty() && password == confirmPassword) {
                    isLoading = true
                    errorMessage = null

                    // Lógica de registro con Firebase
                    coroutineScope.launch {
                        try {
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(email, password)
                                .await() // Espera al resultado de Firebase
                            onRegisterSuccess() // Navega a la siguiente pantalla
                        } catch (e: Exception) {
                            errorMessage = "Error: ${e.message}" // Muestra el error
                        } finally {
                            isLoading = false
                        }
                    }
                } else {
                    errorMessage = "Las contraseñas no coinciden o el email está vacío."
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = purpleColor,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Deshabilitar mientras carga
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(text = "Registrarse")
            }
        }
    }
}
