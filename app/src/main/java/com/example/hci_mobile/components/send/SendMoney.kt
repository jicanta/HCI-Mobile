package com.example.hci_mobile.components.send

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
        },
        containerColor = AppTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
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
                onSendMoney = { amount, description, paymentMethod, email ->
                    when {
                        paymentMethod == accountBalanceString -> {
                            viewModel.makePayment(
                                amount = amount,
                                description = description,
                                type = PaymentType.BALANCE,
                                receiverEmail = email
                            ){
                                onNavigateToRoute(AppDestinations.HOME.route)
                            }
                        }
                        paymentMethod == paymentLinkString -> {
                            viewModel.makePayment(
                                amount = amount,
                                description = description,
                                type = PaymentType.LINK
                            ){
                                onNavigateToRoute(AppDestinations.HOME.route)
                            }
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
                                receiverEmail = email
                            ){
                                onNavigateToRoute(AppDestinations.HOME.route)
                            }
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
    onSendMoney: (Double, String, String, String?) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
    onNavigateToRoute: (String) -> Unit
) {
    val uiState = viewModel.uiState

    var selectedPaymentMethod by rememberSaveable  { mutableStateOf("") }
    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    var amount by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    val paymentLinkString = stringResource(R.string.payment_link)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .align(Alignment.Center),
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
                        onSendMoney(
                            amount.toDouble(),
                            description,
                            selectedPaymentMethod,
                            if (selectedPaymentMethod != paymentLinkString) email else null
                        )
                        onNavigateToRoute(AppDestinations.HOME.route)
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
    }
}

@Preview
@Composable
fun SendScreenPreview() {
    AppTheme(darkTheme = false) {
        //SendScreen(onNavigateBack = {})
    }
}


