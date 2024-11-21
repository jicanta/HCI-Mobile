package com.example.hci_mobile.api.data.network.api


import com.example.hci_mobile.api.data.network.model.NetworkCredentials
import com.example.hci_mobile.api.data.network.model.NetworkToken
import com.example.hci_mobile.api.data.network.model.NetworkUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {

    //antes de llamar a algun metodo hacer: RetrofitClient.getUserApiService(context)

    @POST("user/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>
                                                                       //tiene @Body xq lo recibe la API en formato JSON
    @POST("user/logout")
    suspend fun logout(): Response<Unit>

    @GET("user")
    suspend fun getCurrentUser(): Response<NetworkUser>
}