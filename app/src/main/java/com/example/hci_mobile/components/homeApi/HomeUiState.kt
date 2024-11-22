package com.example.hci_mobile.components.homeApi

import com.example.hci_mobile.api.data.model.ApiError
import com.example.hci_mobile.api.data.model.Card
import com.example.hci_mobile.api.data.model.User

data class HomeUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val cards: List<Card>? = null,
    val currentCard: Card? = null,
    val error: ApiError? = null
)

val HomeUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val HomeUiState.canGetAllCards: Boolean get() = isAuthenticated
val HomeUiState.canAddCard: Boolean get() = isAuthenticated
val HomeUiState.canDeleteCard: Boolean get() = isAuthenticated && currentCard != null