package com.example.hci_mobile.api.data.model

import java.util.Date

data class Movement(
    val type: MovementType,
    val amount: Double,
    val createdAt: Date,
    val payer: User,
    val receiver: User
    )
