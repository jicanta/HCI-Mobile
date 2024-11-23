package com.example.hci_mobile.api.data.network

import com.example.hci_mobile.api.data.network.api.WalletApiService
import com.example.hci_mobile.api.data.network.model.NetworkAmount
import com.example.hci_mobile.api.data.network.model.NetworkBalance
import com.example.hci_mobile.api.data.network.model.NetworkCard
import com.example.hci_mobile.api.data.network.model.NetworkWallet


class WalletRemoteDataSource(
    private val walletApiService: WalletApiService
) : RemoteDataSource() {

    suspend fun getCards(): List<NetworkCard> {
        return handleApiResponse {
            walletApiService.getCards()
        }
    }

    suspend fun addCard(card: NetworkCard): NetworkCard {
        return handleApiResponse {
            walletApiService.addCard(card)
        }
    }

    suspend fun deleteCard(cardId: Int) {
        handleApiResponse {
            walletApiService.deleteCard(cardId)
        }
    }

    suspend fun getBalance(): NetworkBalance {
        return handleApiResponse {
            walletApiService.getBalance()
        }
    }

    suspend fun recharge(amount: Double){
        walletApiService.recharge(NetworkAmount(amount))
    }

    suspend fun getWalletDetails(): NetworkWallet {
        return handleApiResponse {
            walletApiService.getWalletDetails()
        }
    }
}