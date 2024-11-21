package com.example.hci_mobile

import android.app.Application
import com.example.hci_mobile.api.data.network.UserRemoteDataSource
import com.example.hci_mobile.api.data.network.WalletRemoteDataSource
import com.example.hci_mobile.api.data.network.api.RetrofitClient
import com.example.hci_mobile.api.data.repository.UserRepository
import com.example.hci_mobile.api.data.repository.WalletRepository


class MyApplication : Application() {

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getUserApiService(this))

    private val walletRemoteDataSource: WalletRemoteDataSource
        get() = WalletRemoteDataSource(RetrofitClient.getWalletApiService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val walletRepository: WalletRepository
        get() = WalletRepository(walletRemoteDataSource)
}