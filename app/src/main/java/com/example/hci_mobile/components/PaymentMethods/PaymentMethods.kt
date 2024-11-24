package com.example.hci_mobile.components.PaymentMethods

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.R
import com.example.hci_mobile.api.data.model.Card
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.home_screen.getCardColor
import com.example.hci_mobile.components.home_screen.getCardIcon
import com.example.hci_mobile.components.navigation.AppDestinations
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import com.example.hci_mobile.components.navigation.ResponsiveNavigation


@Composable
fun PaymentMethodsScreen(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = { TopBarWithBack(R.string.payment_methods, onNavigateBack = onNavigateBack) },
        bottomBar = {
            if (!isTablet || !isLandscape) {
                ResponsiveNavigation(
                    currentRoute = currentRoute,
                    onNavigateToRoute = onNavigateToRoute,
                    modifier = Modifier
                )
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isTablet && isLandscape) {
                ResponsiveNavigation(
                    currentRoute = currentRoute,
                    onNavigateToRoute = onNavigateToRoute,
                    modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
                )
            }
            
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(AppTheme.colorScheme.background)
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = 600.dp)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        PaymentMethodList()
                        AddPaymentMethodButton(onNavigateToRoute = onNavigateToRoute)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PaymentMethodsScreenPreview() {
    AppTheme {
        //PaymentMethodsScreen()
    }
}

@Composable
fun PaymentMethodList(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    var selectedCard by remember { mutableStateOf<Card?>(null) }
    val uiState = viewModel.uiState
    var cards by remember { mutableStateOf<List<Card>>(emptyList()) }

    // Initial load of cards
    LaunchedEffect(Unit) {
        viewModel.getCards()
    }

    // Update local cards list when uiState.cards changes
    LaunchedEffect(uiState.cards) {
        uiState.cards?.let {
            cards = it
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = AppTheme.shape.container,
        color = AppTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AnimatedVisibility(
                visible = cards.isEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = stringResource( R.string.no_registered_cards),
                    style = AppTheme.typography.body,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
            }

            AnimatedContent(
                targetState = cards,
                transitionSpec = {
                    fadeIn() + slideInVertically() togetherWith
                            fadeOut() + slideOutVertically()
                }
            ) { currentCards ->
                Column {
                    currentCards.forEach { card ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Column {
                                PaymentMethodItem(
                                    card = card,
                                    isSelected = selectedCard == card,
                                    onCardClick = {
                                        selectedCard = if (selectedCard == card) null else card
                                    },
                                    onDeleteCard = {
                                        card.id?.let { cardId ->
                                            // Update local state immediately
                                            cards = cards.filter { it.id != cardId }
                                            selectedCard = null
                                            // Then update backend
                                            viewModel.deleteCard(cardId)
                                        }
                                    }
                                )
                                if (card != currentCards.last()) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentMethodItem(
    card: Card,
    isSelected: Boolean,
    onCardClick: () -> Unit,
    onDeleteCard: () -> Unit
) {
    val cardType = getCardType(card.number)
    val cardElevation = if (isSelected) 12.dp else 4.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = AppTheme.shape.container,
        colors = CardDefaults.cardColors(containerColor = getCardColor(type = cardType)),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = getCardIcon(type = cardType)),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = formatCardNumber(card.number),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        style = AppTheme.typography.body
                    )
                    Text(
                        text = card.fullName,
                        color = Color.White,
                        fontSize = 14.sp,
                        style = AppTheme.typography.body
                    )
                    Text(
                        text = "${R.string.expires}: ${card.expirationDate}",
                        color = Color.White,
                        fontSize = 12.sp,
                        style = AppTheme.typography.body
                    )
                }
            }

            AnimatedVisibility(visible = isSelected) {
                Button(
                    onClick = onDeleteCard,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.delete),
                        color = Color.White
                    )
                }
            }
        }
    }
}

// Función para formatear el número de tarjeta
private fun formatCardNumber(number: String): String {
    val digitsOnly = number.filter { it.isDigit() }
    return if (digitsOnly.length >= 4) {
        val lastFourDigits = digitsOnly.takeLast(4)
        "**** $lastFourDigits"
    } else {
        "**** ****"
    }
}

// Función para determinar el tipo de tarjeta basado en el BIN
fun getCardType(number: String): String {
    val prefix = number.take(2)
    return when {
        number.startsWith("4") -> "VISA"
        number.startsWith("51") || number.startsWith("52") ||
                number.startsWith("53") || number.startsWith("54") ||
                number.startsWith("55") -> "MASTERCARD"
        number.startsWith("34") || number.startsWith("37") -> "AMERICAN EXPRESS"
        else -> "UNKNOWN"
    }
}

@Composable
fun AddPaymentMethodButton(
    onNavigateToRoute: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 600.dp)
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .background(AppTheme.colorScheme.background, AppTheme.shape.container)
            .border(2.dp, AppTheme.colorScheme.tertiary, AppTheme.shape.container)
            .clickable { onNavigateToRoute(AppDestinations.ADDCARD.route) }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Añadir método de pago",
                tint = AppTheme.colorScheme.tertiary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.add),
                color = AppTheme.colorScheme.tertiary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                style = AppTheme.typography.body
            )
        }
    }
}

data class PaymentMethod(
    val type: String,
    val ownerName: String,
    val lastFourDigits: String
)