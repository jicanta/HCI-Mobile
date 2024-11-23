package com.example.hci_mobile.api.data.network.model

import kotlinx.serialization.Serializable

@Serializable
enum class PaymentType {
    CARD,
    BALANCE,
    LINK
}

@Serializable
data class NetworkPayment(
    val amount: Double,
    val description: String,
    val type: PaymentType,
    val cardId: Int? = null,
    val receiverEmail: String? = null
)
