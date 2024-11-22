package com.example.hci_mobile.components.homeApi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.SessionManager
import com.example.hci_mobile.api.data.repository.UserRepository
import com.example.hci_mobile.api.data.repository.WalletRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.hci_mobile.api.data.model.Card
import com.example.hci_mobile.api.data.model.ApiError
import com.example.hci_mobile.api.data.DataSourceException

private val Throwable.code: Int?    //TODO: a chequear si esta bien, lo
    get() {
        return (this as? DataSourceException)?.code
    }

class HomeViewModel(
    sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun register(firstName: String, lastName: String, birthDate: String , email: String, password: String) = runOnViewModelScope(
        { userRepository.register(firstName, lastName, birthDate, email, password) },
        { state, response -> state.copy(currentUser = response) }
    )

    fun verify(token: String) = runOnViewModelScope(
        { userRepository.verify(token) },
        { state, response -> state.copy(currentUser = response) }
    )

    fun login(username: String, password: String) = runOnViewModelScope(
        { userRepository.login(username, password) },
        { state, _ -> state.copy(isAuthenticated = true) }
    )

    fun logout() = runOnViewModelScope(
        { userRepository.logout() },
        { state, _ ->
            state.copy(
                isAuthenticated = false,
                currentUser = null,
                currentCard = null,
                cards = null
            )
        }
    )

    fun getCurrentUser() = runOnViewModelScope(
        { userRepository.getCurrentUser(uiState.currentUser == null) },
        { state, response -> state.copy(currentUser = response) }
    )

    fun getCards() = runOnViewModelScope(
        { walletRepository.getCards(true) },
        { state, response -> state.copy(cards = response) }
    )

    fun addCard(card: Card) = runOnViewModelScope(
        {
            walletRepository.addCard(card)
        },
        { state, response ->
            state.copy(
                currentCard = response,
                cards = null
            )
        }
    )

    fun deleteCard(cardId: Int) = runOnViewModelScope(
        { walletRepository.deleteCard(cardId) },
        { state, _ ->
            state.copy(
                currentCard = null,
                cards = null
            )
        }
    )

    fun getBalance() = runOnViewModelScope(
        { walletRepository.getBalance() },
        { state, response -> state.copy(balance = response) }
    )

    fun recharge(amount: Double) = runOnViewModelScope(
        { walletRepository.recharge(amount)},
        {state, _ -> state}
    )

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (HomeUiState, R) -> HomeUiState
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isFetching = true, error = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isFetching = false)
        }.onFailure { e ->
            uiState = uiState.copy(isFetching = false, error = handleError(e))
            Log.e(TAG, "Coroutine execution failed", e)
        }
    }

    private fun handleError(e: Throwable): ApiError {
        return if (e is DataSourceException) {
            ApiError(e.code, e.message ?: "")
        } else {
            ApiError(null, e.message ?: "")
        }
    }

    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            application: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(
                    application.sessionManager,
                    application.userRepository,
                    application.walletRepository) as T
            }
        }
    }
}