package com.example.hci_mobile.api.data.model

import java.util.Date

data class Wallet(
    val id: Int?,
    val balance: Double,
    val invested: Double,
    val cbu: String,
    val alias: String,
    val createdAt: Date,
    val updatedAt: Date
    )
