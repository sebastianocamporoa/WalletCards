package com.example.walletcards.ui.screens.main

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import com.example.walletcards.data.repository.BusinessCardRepository
import com.example.walletcards.ui.components.BusinessCardItem

@Composable
fun MainScreen(repository: BusinessCardRepository) {
    val cards = repository.getCards() // Obtener las tarjetas del repositorio compartido

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(cards.size) { index ->
            BusinessCardItem(card = cards[index])
        }
    }
}
