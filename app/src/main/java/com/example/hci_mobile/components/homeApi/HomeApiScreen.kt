package com.example.hci_mobile.components.homeApi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.R
import com.example.hci_mobile.api.data.model.Card
import com.example.hci_mobile.api.data.model.CardType
import kotlin.random.Random
import com.example.hci_mobile.MyApplication

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState

   /* Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        if (!uiState.isAuthenticated) {
            ActionButton(
                resId = R.string.login,
                onClick = {
                    viewModel.login("johndoe@email.com", "1234567890")
                })
        } else {
            ActionButton(
                resId = R.string.logout,
                onClick = {
                    viewModel.logout()
                })
        }
        ActionButton(
            resId = R.string.get_current_user,
            enabled = uiState.canGetCurrentUser,
            onClick = {
                viewModel.getCurrentUser()
            })
        ActionButton(
            resId = R.string.get_all_cards,
            enabled = uiState.canGetAllCards,
            onClick = {
                viewModel.getCards()
            })
        ActionButton(
            resId = R.string.add_card,
            enabled = uiState.canAddCard,
            onClick = {
                val random = Random.nextInt(0, 9999)
                val card = Card(number = "499003140861${random.toString().padStart(4, '0')}",
                    fullName = "Christeen Mischke",
                    expirationDate = "05/28",
                    cvv = "215",
                    type = CardType.CREDIT)
                viewModel.addCard(card)
            })
        ActionButton(
            resId = R.string.delete_card,
            enabled = uiState.canDeleteCard,
            onClick = {
                val currentCard = uiState.currentCard!!
                viewModel.deleteCard(currentCard.id!!)
            })
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val currentUserData = uiState.currentUser?.let {
                "Current User: ${it.firstName} ${it.lastName} (${it.email})"
            }
            Text(
                text = currentUserData ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 18.sp
            )
            Text(
                text = "Total Cards: ${uiState.cards?.size ?: "unknown"}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 18.sp
            )
            if (uiState.error != null) {
                Text(
                    text = "${uiState.error.code} - ${uiState.error.message}",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    fontSize = 18.sp
                )
            }
        }
    }*/
}