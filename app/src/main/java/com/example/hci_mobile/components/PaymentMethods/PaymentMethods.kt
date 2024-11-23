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


@Composable
fun PaymentMethodsScreen(
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit,
    onNavigateBack: () -> Unit
) {

    Scaffold(
        topBar = { TopBarWithBack(R.string.payment_methods, onNavigateBack = onNavigateBack) },
        bottomBar = { BottomBar(currentRoute = currentRoute, onNavigateToRoute = onNavigateToRoute) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            PaymentMethodList()
            AddPaymentMethodButton(onNavigateToRoute = onNavigateToRoute)
            Spacer(modifier = Modifier.height(16.dp))
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
    val uiState = viewModel.uiState
    
    LaunchedEffect(Unit) {
        viewModel.getCards()
        while(true) {
            delay(30000) // 30 segundos
            viewModel.getCards()
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            uiState.cards?.forEach { card ->
                PaymentMethodItem(card)
                if (card != uiState.cards.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun PaymentMethodItem(card: Card) {
    val cardType = getCardType(card.number)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(getCardColor(type = cardType), AppTheme.shape.container)
            .padding(16.dp),
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
                text = "Vence: ${card.expirationDate}",
                color = Color.White,
                fontSize = 12.sp,
                style = AppTheme.typography.body
            )
        }
        IconButton(onClick = { /* TODO: Implementar eliminación */ }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar método de pago",
                tint = Color.White
            )
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
private fun getCardType(number: String): String {
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
                text = stringResource(R.string.add_payment_method),
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
