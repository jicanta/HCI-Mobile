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
    var currentUser: User? = null,  //TODO: chequear xq es var
    val cards: List<Card>? = null,
    val currentCard: Card? = null,
    val error: ApiError? = null,
    val balance: Balance? = null,
    var wallet: Wallet? = null    //TODO: chequear xq es var
)

val HomeUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val HomeUiState.canGetAllCards: Boolean get() = isAuthenticated
val HomeUiState.canAddCard: Boolean get() = isAuthenticated
val HomeUiState.canDeleteCard: Boolean get() = isAuthenticated && currentCard != null