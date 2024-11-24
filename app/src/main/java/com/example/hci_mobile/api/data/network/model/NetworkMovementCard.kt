package com.example.hci_mobile.api.data.network.model

import kotlinx.serialization.Serializable

@Serializable
class NetworkMovementCard(
    var id: Int?,
    var number: String,
    var expirationDate: String,
    var fullName: String,
    var type: String
)
