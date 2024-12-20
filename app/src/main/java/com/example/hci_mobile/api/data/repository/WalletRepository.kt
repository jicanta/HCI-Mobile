package com.example.hci_mobile.api.data.repository

import com.example.hci_mobile.api.data.model.Balance
import com.example.hci_mobile.api.data.model.Card
import com.example.hci_mobile.api.data.model.NewBalance
import com.example.hci_mobile.api.data.model.Wallet
import com.example.hci_mobile.api.data.network.WalletRemoteDataSource
import com.example.hci_mobile.api.data.network.model.NetworkBalance
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class WalletRepository(
    private val remoteDataSource: WalletRemoteDataSource
) {
    // Mutex to make writes to cached values thread-safe.
    private val cardsMutex = Mutex()
    // Cache of the latest sports got from the network.
    private var cards: List<Card> = emptyList()

    suspend fun getCards(refresh: Boolean = false): List<Card> {
        if (refresh || cards.isEmpty()) {
            val result = remoteDataSource.getCards()
            // Thread-safe write to sports
            cardsMutex.withLock {
                this.cards = result.map { it.asModel() }
            }
        }

        return cardsMutex.withLock { this.cards }
    }

    suspend fun addCard(card: Card) : Card {
        val newCard = remoteDataSource.addCard(card.asNetworkModel()).asModel()
        cardsMutex.withLock {
            this.cards = emptyList()
        }
        return newCard
    }

    suspend fun deleteCard(cardId: Int) {
        remoteDataSource.deleteCard(cardId)
        cardsMutex.withLock {
            this.cards = emptyList()
        }
    }

    suspend fun getBalance(): Balance {
        val result =  remoteDataSource.getBalance()
        return result.asModel()
    }

    suspend fun recharge(amount: Double): NewBalance {
        val result = remoteDataSource.recharge(amount).asModel()
        return result
    }

    suspend fun getWalletDetails(): Wallet {
        val result =  remoteDataSource.getWalletDetails()
        return result.asModel()
    }

    suspend fun updateAlias(alias: String){
        remoteDataSource.updateAlias(alias)
    }

}