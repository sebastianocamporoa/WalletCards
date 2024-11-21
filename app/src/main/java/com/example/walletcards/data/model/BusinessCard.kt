package com.example.walletcards.data.model

import android.net.Uri

data class BusinessCard(
    val id: Int,
    val userId: String = "",
    val name: String,
    val company: String,
    val position: String,
    val phone: String,
    val email: String,
    val photoUri: Uri? = null
)
