package com.example.hci_mobile.components.send

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.R
import com.example.hci_mobile.api.data.model.ApiError
import com.example.hci_mobile.api.data.model.Card
import com.example.hci_mobile.api.data.network.model.PaymentType
import com.example.hci_mobile.components.PaymentMethods.getCardType
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.navigation.AppDestinations
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendScreen(
    onNavigateBack: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
    onNavigateToRoute: (String) -> Unit
) {

    val uiState = viewModel.uiState
    val accountBalanceString = stringResource(R.string.account_balance)
    val paymentLinkString = stringResource(R.string.payment_link)

    LaunchedEffect(Unit) {
        viewModel.getCards()
    }

    val paymentOptions = listOf(accountBalanceString) + 
        (uiState.cards?.map { formatCardInfo(it) } ?: emptyList()) +
        listOf(paymentLinkString)

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = R.string.send,
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            SendMoneyCard(
                cards = paymentOptions,
                onSendMoney = { amount, description, paymentMethod, email, onNavigateToRoute ->
                    when {
                        paymentMethod == accountBalanceString -> {
                            viewModel.makePayment(
                                amount = amount, 
                                description = description, 
                                type = PaymentType.BALANCE, 
                                receiverEmail = email,
                                onSucessfullPayment = { onNavigateToRoute(AppDestinations.HOME.route) }
                            )
                        }
                        paymentMethod == paymentLinkString -> {
                            viewModel.makePayment(
                                amount = amount, 
                                description = description, 
                                type = PaymentType.LINK,
                                onSucessfullPayment = { onNavigateToRoute(AppDestinations.HOME.route) }
                            )
                        }
                        else -> {
                            val selectedCard = uiState.cards?.find { 
                                formatCardInfo(it) == paymentMethod 
                            }
                            viewModel.makePayment(
                                amount = amount, 
                                description = description, 
                                type = PaymentType.CARD, 
                                cardId = selectedCard?.id, 
                                receiverEmail = email,
                                onSucessfullPayment = { onNavigateToRoute(AppDestinations.HOME.route) }
                            )
                        }
                    }
                },
                onNavigateToRoute = onNavigateToRoute
            )
        }
    }
}

// Función auxiliar para formatear la información de la tarjeta
private fun formatCardInfo(card: Card): String {
    val digitsOnly = card.number.filter { it.isDigit() }
    val lastFourDigits = digitsOnly.takeLast(4)
    val cardType = getCardType(card.number)
    return "****-$lastFourDigits ($cardType)"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyCard(
    modifier: Modifier = Modifier,
    cards: List<String>,
    onSendMoney: (Double, String, String, String?, () -> Unit) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
    onNavigateToRoute: (String) -> Unit
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            error.message?.let {
                snackbarHostState.showSnackbar(
                    message = it,
                    duration = SnackbarDuration.Short
                )
            }
            viewModel.clearError()
        }
    }

    // Observar el éxito de la llamada
    /*LaunchedEffect(uiState.callSuccess) {
        if (uiState.callSuccess) {
            //Log.d("SendMoneyCard", "Navegando a HOME")
            try {
                onNavigateToRoute(AppDestinations.HOME.route)
            } finally {
                viewModel.clearCallSuccess()
            }
        }
    }*/

    var selectedPaymentMethod by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val paymentLinkString = stringResource(R.string.payment_link)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center  // Esto centrará el contenido
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .align(Alignment.Center),  // Esto asegura que la Card esté centrada
            shape = AppTheme.shape.container,
            colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.secondary),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                            amount = newValue
                        }
                    },
                    label = { Text(
                        text = stringResource(R.string.enter_amount),
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.textColor
                    ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AppTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                        disabledContainerColor = AppTheme.colorScheme.onTertiary,
                        focusedTextColor = AppTheme.colorScheme.textColor,
                        unfocusedTextColor = AppTheme.colorScheme.textColor,
                        focusedBorderColor = AppTheme.colorScheme.tertiary,
                        unfocusedBorderColor = AppTheme.colorScheme.tertiary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(
                        text = stringResource(R.string.description),
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.textColor
                    ) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AppTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                        disabledContainerColor = AppTheme.colorScheme.onTertiary,
                        focusedTextColor = AppTheme.colorScheme.textColor,
                        unfocusedTextColor = AppTheme.colorScheme.textColor,
                        focusedBorderColor = AppTheme.colorScheme.tertiary,
                        unfocusedBorderColor = AppTheme.colorScheme.tertiary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedPaymentMethod,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text(
                            text = stringResource(R.string.payment_methods),
                            style = AppTheme.typography.body,
                            color = AppTheme.colorScheme.textColor
                        ) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AppTheme.colorScheme.onTertiary,
                            unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                            disabledContainerColor = AppTheme.colorScheme.onTertiary,
                            focusedTextColor = AppTheme.colorScheme.textColor,
                            unfocusedTextColor = AppTheme.colorScheme.textColor,
                            focusedBorderColor = AppTheme.colorScheme.tertiary,
                            unfocusedBorderColor = AppTheme.colorScheme.tertiary
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier.background(AppTheme.colorScheme.onTertiary)
                    ) {
                        cards.forEach { paymentMethod ->
                            DropdownMenuItem(
                                text = { Text(
                                    text = paymentMethod,
                                    style = AppTheme.typography.body,
                                    color = AppTheme.colorScheme.textColor
                                ) },
                                onClick = {
                                    selectedPaymentMethod = paymentMethod
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                if (selectedPaymentMethod != "" && selectedPaymentMethod != paymentLinkString) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(
                            text = stringResource(R.string.email),
                            style = AppTheme.typography.body
                        ) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AppTheme.colorScheme.onTertiary,
                            unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                            disabledContainerColor = AppTheme.colorScheme.onTertiary,
                            focusedTextColor = AppTheme.colorScheme.textColor,
                            unfocusedTextColor = AppTheme.colorScheme.textColor,
                            focusedBorderColor = AppTheme.colorScheme.tertiary,
                            unfocusedBorderColor = AppTheme.colorScheme.tertiary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        try {
                            onSendMoney(
                                amount.toDouble(),
                                description,
                                selectedPaymentMethod,
                                if (selectedPaymentMethod != paymentLinkString) email else null,
                                { onNavigateToRoute(AppDestinations.HOME.route) }
                            )
                            //onNavigateToRoute(AppDestinations.HOME.route)
                        } catch (e: Exception) {
                            viewModel.setError(ApiError(
                                code = 400,
                                message = e.message ?: "Error al procesar el pago"
                            ))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = AppTheme.shape.button,
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primary),
                    enabled = amount.isNotEmpty() && 
                             amount.toDoubleOrNull() != null && 
                             description.isNotEmpty() && 
                             selectedPaymentMethod.isNotEmpty() &&
                             (selectedPaymentMethod == paymentLinkString || email.isNotEmpty())
                ) {
                    Text(
                        text = stringResource(id = R.string.send),
                        color = AppTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        style = AppTheme.typography.body
                    )
                }
            }
        }

        // Snackbar host
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) { data ->
            Snackbar(
                containerColor = AppTheme.colorScheme.primary,
                contentColor = AppTheme.colorScheme.onPrimary,
            ) {
                Text(
                    text = data.visuals.message,
                    style = AppTheme.typography.body
                )
            }
        }
    }
}

@Preview
@Composable
fun SendScreenPreview() {
    AppTheme(darkTheme = false) {
        //SendScreen(onNavigateBack = {})
    }
}

