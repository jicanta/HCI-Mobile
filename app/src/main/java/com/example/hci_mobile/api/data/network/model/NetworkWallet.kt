package com.example.hci_mobile.api.data.network.model

import com.example.hci_mobile.api.data.model.Wallet
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class NetworkWallet(
    var id: Int?,
    var balance: Double,
    var invested: Double,
    var cbu: String,
    var alias: String,
    var createdAt: String,
    var updatedAt: String
){
    fun asModel(): Wallet {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return Wallet(
            id = id,
            balance = balance,
            invested = invested,
            cbu = cbu,
            alias = alias,
            createdAt = dateFormat.parse(createdAt)!!,
            updatedAt = dateFormat.parse(updatedAt)!!
        )
    }
}
