package com.example.walletcards.data.repository

import com.example.walletcards.data.model.BusinessCard

class BusinessCardRepository {
    private val businessCards = mutableListOf<BusinessCard>()

    fun getCards(): List<BusinessCard> = businessCards

    fun addCard(card: BusinessCard) {
        businessCards.add(card)
    }

    fun hasCards(): Boolean = businessCards.isNotEmpty()
}
