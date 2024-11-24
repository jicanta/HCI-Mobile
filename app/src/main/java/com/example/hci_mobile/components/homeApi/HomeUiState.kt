package com.example.hci_mobile.components.homeApi

import com.example.hci_mobile.api.data.model.ApiError
import com.example.hci_mobile.api.data.model.Balance
import com.example.hci_mobile.api.data.model.Card
import com.example.hci_mobile.api.data.model.User
import com.example.hci_mobile.api.data.model.Wallet
import com.example.hci_mobile.api.data.network.model.NetworkBalance

data class HomeUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val cards: List<Card>? = null,
    val currentCard: Card? = null,
    var error: ApiError? = null,  //TODO: chequear si esta bien que sea asi
    val balance: Balance? = null,
    val wallet: Wallet? = null
)

val HomeUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val HomeUiState.canGetAllCards: Boolean get() = isAuthenticated
val HomeUiState.canAddCard: Boolean get() = isAuthenticated
val HomeUiState.canDeleteCard: Boolean get() = isAuthenticated && currentCard != null