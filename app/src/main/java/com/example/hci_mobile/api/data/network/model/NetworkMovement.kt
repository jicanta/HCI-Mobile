package com.example.hci_mobile.api.data.network.model

import com.example.hci_mobile.api.data.model.Movement
import com.example.hci_mobile.api.data.model.MovementType
import com.example.hci_mobile.api.data.model.User
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkMovement(
    val id: Int,
    val type: String,
    val amount: Double,
    val balanceBefore: Double,
    val balanceAfter: Double,
    val pending: Boolean,
    val linkUuid: Boolean?,
    val createdAt: String,
    val updatedAt: String,
    val card: NetworkMovementCard?,
    val payer: NetworkMovementUser,
    val receiver: NetworkMovementUser
){
    fun asModel(): Movement {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return Movement(
            type =
            if(type == "BALANCE"){
                MovementType.BALANCE
            }else{
                MovementType.CARD
            },
            amount = amount,
            createdAt = dateFormat.parse(createdAt)!!,
            payer = payer.asModel(),
            receiver = receiver.asModel()
        )
    }
}
