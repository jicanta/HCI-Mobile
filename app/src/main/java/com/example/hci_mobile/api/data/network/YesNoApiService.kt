package com.example.hci_mobile.api.data.network

import com.example.hci_mobile.api.data.model.YesNoAnswer
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

//ACA MODIFICO EL CANAL

private const val BASE_URL = "https://yesno.wtf/"  //muy importante el '/' al final

private val httpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)   //todos los mensajes de httpLogging los interecepta
    .build()

//lo que pasa del json al data class
private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface YesNoApiService { //metodos de la api -> IMPORTANTISIMO EL suspend -> sino se hace bloqueante
    @GET("api")
    suspend fun getAnswer(): YesNoAnswer
}

object YesNoApi {
    val retrofitService: YesNoApiService by lazy {
        retrofit.create(YesNoApiService::class.java)
    }
}

/*
    @GET
    @POST
    @PUT
    @DELETE
*/