package com.example.hci_mobile.api.data.network.model

import kotlinx.serialization.Serializable

@Serializable
class NetworkPaymentsResponse(
    val payments: List<NetworkMovement>
)
