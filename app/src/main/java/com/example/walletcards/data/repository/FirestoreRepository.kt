package com.example.walletcards.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class BusinessCard(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val company: String = "",
    val position: String = "",
    val phone: String = "",
    val photoUri: String? = null
)

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val cardsCollection = db.collection("business_cards")

    private fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    suspend fun getUserCards(): List<BusinessCard> {
        val userId = getCurrentUserId() ?: throw Exception("Usuario no autenticado")
        return cardsCollection.whereEqualTo("userId", userId)
            .get()
            .await()
            .toObjects(BusinessCard::class.java)
    }

    suspend fun createCard(card: BusinessCard) {
        val userId = getCurrentUserId() ?: throw Exception("Usuario no autenticado")
        val newCard = card.copy(userId = userId) // Asignar el userId al crear la tarjeta
        cardsCollection.add(newCard).await()
    }
}
