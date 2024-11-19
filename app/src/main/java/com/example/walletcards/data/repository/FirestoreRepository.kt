package com.example.walletcards.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Modelo de datos para una tarjeta de presentación
data class BusinessCard(
    val id: String = "",
    val name: String = "",
    val company: String = "",
    val position: String = "",
    val phone: String = "",
    val photoUri: String? = null
)

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val cardsCollection = db.collection("business_cards")

    // Obtener todas las tarjetas
    suspend fun getAllCards(): List<BusinessCard> {
        return cardsCollection.get().await().toObjects(BusinessCard::class.java)
    }

    // Crear una nueva tarjeta
    suspend fun createCard(card: BusinessCard) {
        cardsCollection.add(card).await()
    }
}
