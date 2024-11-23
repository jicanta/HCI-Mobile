package com.example.hci_mobile.api.data.network.api

import com.example.hci_mobile.api.data.network.model.NetworkPayment
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface PaymentApiService {

    @POST("payment")
    suspend fun makePayment(@Body paymentRequest: NetworkPayment): Response<Unit>

}