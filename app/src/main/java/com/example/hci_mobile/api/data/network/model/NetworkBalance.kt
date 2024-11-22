package com.example.hci_mobile.api.data.network.model

import com.example.hci_mobile.api.data.model.Balance

import kotlinx.serialization.Serializable

@Serializable
data class NetworkBalance(
    var balance: Double
){
    fun asModel(): Balance{
        return Balance(
            balance = balance
        )
    }
}
