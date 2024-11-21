package com.example.walletcards.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.walletcards.data.repository.BusinessCard
import com.example.walletcards.data.repository.FirestoreRepository
import com.example.walletcards.ui.components.BusinessCardItem
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val repository = FirestoreRepository()
    var cards by remember { mutableStateOf<List<BusinessCard>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Corutina para cargar las tarjetas al iniciar la pantalla
    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null
        try {
            cards = repository.getUserCards() // Obtener solo las tarjetas del usuario actual
        } catch (e: Exception) {
            errorMessage = "Error al cargar las tarjetas: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    // Contenido de la pantalla
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                // Indicador de carga mientras se obtienen las tarjetas
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMessage != null -> {
                // Mostrar mensaje de error si ocurrió un problema
                Text(
                    text = errorMessage ?: "Error desconocido",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            cards.isEmpty() -> {
                // Mostrar mensaje si no hay tarjetas
                Text(
                    text = "No tienes tarjetas creadas aún.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                // Mostrar la lista de tarjetas
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cards.size) { index ->
                        BusinessCardItem(card = cards[index])
                    }
                }
            }
        }
    }
}
