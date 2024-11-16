package com.example.walletcards.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.walletcards.data.model.BusinessCard

@Composable
fun BusinessCardItem(card: BusinessCard) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = card.name)
            Text(text = card.company)
            Text(text = card.position)
            Text(text = card.phone)
            Text(text = card.email)
        }
    }
}
