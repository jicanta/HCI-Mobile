package com.example.hci_mobile.components.deposit_money
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.hci_mobile.api.data.model.Card
import com.example.hci_mobile.components.PaymentMethods.getCardType
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme
import androidx.compose.runtime.LaunchedEffect

// Función auxiliar para formatear la información de la tarjeta
private fun formatCardInfo(card: Card): String {
    val digitsOnly = card.number.filter { it.isDigit() }
    val lastFourDigits = digitsOnly.takeLast(4)
    val cardType = getCardType(card.number)
    return "****-$lastFourDigits ($cardType)"
}

@Composable
fun DepositMoneyScreen(
    onNavigateBack: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState
    val externalFundsString = stringResource(R.string.external_funds)

    // Cargar las tarjetas al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.getCards()
    }

    // Crear lista de opciones de pago
    val paymentOptions = listOf(externalFundsString) + 
        (uiState.cards?.map { formatCardInfo(it) } ?: emptyList())

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = R.string.add_money,
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
            DepositMoneyCard(
                cards = paymentOptions,
                onRecharge = { amount ->
                    viewModel.recharge(amount)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositMoneyCard(
    modifier: Modifier = Modifier,
    cards: List<String>,
    onRecharge: (Double) -> Unit
) {
    var selectedPaymentMethod by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight(),
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
                label = { 
                    Text(
                        text = stringResource(id = R.string.enter_amount),
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.textColor
                    ) 
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                    disabledContainerColor = AppTheme.colorScheme.onTertiary,
                    focusedTextColor = AppTheme.colorScheme.textColor,
                    unfocusedTextColor = AppTheme.colorScheme.textColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedPaymentMethod,
                    onValueChange = { },
                    readOnly = true,
                    label = { 
                        Text(
                            text = stringResource(R.string.payment_methods),
                            style = AppTheme.typography.body,
                            color = AppTheme.colorScheme.textColor
                        ) 
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AppTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                        disabledContainerColor = AppTheme.colorScheme.onTertiary,
                        focusedTextColor = AppTheme.colorScheme.textColor,
                        unfocusedTextColor = AppTheme.colorScheme.textColor
                    )
                )
                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    cards.forEach { paymentMethod ->
                        DropdownMenuItem(
                            text = { 
                                Text(
                                    text = paymentMethod,
                                    style = AppTheme.typography.body,
                                    color = AppTheme.colorScheme.textColor
                                ) 
                            },
                            onClick = {
                                selectedPaymentMethod = paymentMethod
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { 
                    if (amount.isNotEmpty() && amount.toDoubleOrNull() != null) {
                        onRecharge(amount.toDouble())
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = AppTheme.shape.button,
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primary),
                enabled = amount.isNotEmpty() && amount.toDoubleOrNull() != null && selectedPaymentMethod.isNotEmpty()
            ) {
                Text(
                    text = stringResource(id = R.string.continuar),
                    color = AppTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = AppTheme.typography.body
                )
            }
        }
    }
}

@Preview
@Composable
fun DepositMoneyScreenPreview() {
    val cards = listOf("Tarjeta 1", "Tarjeta 2", "Tarjeta 3")
    AppTheme(darkTheme = false) {
        DepositMoneyScreen(onNavigateBack = {})
    }
}
