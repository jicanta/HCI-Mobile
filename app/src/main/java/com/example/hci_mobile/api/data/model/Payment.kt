package com.example.hci_mobile.api.data.model

data class Payment(
    val amount: Double,
    val description: String,
    val type: String,
    val cardId: Int? = null,
    val receiverEmail: String? = null
)
