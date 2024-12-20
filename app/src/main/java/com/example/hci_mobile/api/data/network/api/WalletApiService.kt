package com.example.hci_mobile.api.data.network.api


import com.example.hci_mobile.api.data.network.model.NetworkAlias
import com.example.hci_mobile.api.data.network.model.NetworkAmount
import com.example.hci_mobile.api.data.network.model.NetworkBalance
import com.example.hci_mobile.api.data.network.model.NetworkCard
import com.example.hci_mobile.api.data.network.model.NetworkNewBalance
import com.example.hci_mobile.api.data.network.model.NetworkWallet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WalletApiService {


    @GET("wallet/cards")
    suspend fun getCards(): Response<List<NetworkCard>>

    @POST("wallet/cards")
    suspend fun addCard(@Body card: NetworkCard): Response<NetworkCard>

    @DELETE("wallet/cards/{cardId}")
    suspend fun deleteCard(@Path("cardId") cardId: Int): Response<Unit>

    @GET("wallet/balance")
    suspend fun getBalance(): Response<NetworkBalance>

    @POST("wallet/recharge")
    suspend fun recharge(@Body amount: NetworkAmount): Response<NetworkNewBalance>

    @GET("wallet/details")
    suspend fun getWalletDetails(): Response<NetworkWallet>

    @PUT("wallet/update-alias")
    suspend fun updateAlias(@Body alias: NetworkAlias): Response<Unit>



}