package com.example.hci_mobile.api.data.network

import com.example.hci_mobile.api.data.network.api.PaymentApiService
import com.example.hci_mobile.api.data.network.model.NetworkMovement
import com.example.hci_mobile.api.data.network.model.NetworkNewBalance
import com.example.hci_mobile.api.data.network.model.NetworkPayment
import com.example.hci_mobile.api.data.network.model.PaymentType

class PaymentRemoteDataSource(
    private val paymentApiService: PaymentApiService
) : RemoteDataSource() {

    suspend fun makePayment(amount: Double, description: String, type: PaymentType, cardId: Int? = null, receiverEmail: String? = null): NetworkNewBalance {
        return handleApiResponse { paymentApiService.makePayment(NetworkPayment(amount, description, type, cardId, receiverEmail)) }
    }

    suspend fun getPayments(): List<NetworkMovement> {
        return handleApiResponse { paymentApiService.getPayments() }.payments
    }

}