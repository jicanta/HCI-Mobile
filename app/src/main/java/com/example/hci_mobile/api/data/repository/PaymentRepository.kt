package com.example.hci_mobile.api.data.repository

import com.example.hci_mobile.api.data.network.PaymentRemoteDataSource
import com.example.hci_mobile.api.data.network.model.PaymentType

class PaymentRepository(
    private val remoteDataSource: PaymentRemoteDataSource
) {

    suspend fun makePayment(amount: Double, description: String, type: PaymentType, cardId: Int? = null, receiverEmail: String? = null){

        remoteDataSource.makePayment(amount, description, type, cardId, receiverEmail)

    }

}