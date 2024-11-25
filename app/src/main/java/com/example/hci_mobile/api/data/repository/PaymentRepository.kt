package com.example.hci_mobile.api.data.repository

import com.example.hci_mobile.api.data.model.Balance
import com.example.hci_mobile.api.data.model.Movement
import com.example.hci_mobile.api.data.model.NewBalance
import com.example.hci_mobile.api.data.network.PaymentRemoteDataSource
import com.example.hci_mobile.api.data.network.model.PaymentType

class PaymentRepository(
    private val remoteDataSource: PaymentRemoteDataSource
) {

    suspend fun makePayment(amount: Double, description: String, type: PaymentType, cardId: Int? = null, receiverEmail: String? = null): NewBalance {
        return remoteDataSource.makePayment(amount, description, type, cardId, receiverEmail).asModel()
    }

    suspend fun getPayments(): List<Movement> {
        val result =  remoteDataSource.getPayments()
        return result.map { it.asModel() }
    }

}