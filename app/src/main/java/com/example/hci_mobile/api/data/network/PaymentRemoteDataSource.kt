package com.example.hci_mobile.api.data.network

import com.example.hci_mobile.api.data.network.api.PaymentApiService
import com.example.hci_mobile.api.data.network.api.WalletApiService
import com.example.hci_mobile.api.data.network.model.NetworkCard
import com.example.hci_mobile.api.data.network.model.NetworkPayment
import com.example.hci_mobile.api.data.network.model.PaymentType

class PaymentRemoteDataSource(
    private val paymentApiService: PaymentApiService
) : RemoteDataSource() {

    suspend fun makePayment(amount: Double, description: String, type: PaymentType, cardId: Int? = null, receiverEmail: String? = null){
        handleApiResponse { paymentApiService.makePayment(NetworkPayment(amount, description, type, cardId, receiverEmail)) }
    }

}