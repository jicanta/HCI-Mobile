package com.example.hci_mobile.api.data.network.model

import com.example.hci_mobile.api.data.model.Balance
import com.example.hci_mobile.api.data.model.NewBalance
import kotlinx.serialization.Serializable

@Serializable
class NetworkNewBalance (
    val newBalance: Double
){
    fun asModel(): NewBalance {
        return NewBalance(
            newBalance = newBalance
        )
    }
}