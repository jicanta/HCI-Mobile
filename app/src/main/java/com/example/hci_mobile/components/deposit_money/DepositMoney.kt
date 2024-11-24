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
import android.content.res.Configuration
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration


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


    LaunchedEffect(Unit) {
        viewModel.getCards()
    }


    val paymentOptions = listOf(externalFundsString) + 
        (uiState.cards?.map { formatCardInfo(it) } ?: emptyList())

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = R.string.deposit,
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
    // Uso de rememberSaveable para mantener el estado durante recreaciones de la pantalla
    var selectedPaymentMethod by rememberSaveable { mutableStateOf("") }
    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    var amount by rememberSaveable { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val cardWidth = if (isTablet && isLandscape) 0.6f else 0.9f

    Card(
        modifier = Modifier
            .fillMaxWidth(cardWidth)
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
            // Campo para ingresar el monto
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
                    unfocusedTextColor = AppTheme.colorScheme.textColor,
                    focusedBorderColor = AppTheme.colorScheme.tertiary,
                    unfocusedBorderColor = AppTheme.colorScheme.tertiary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Menú desplegable para seleccionar el método de pago
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

            // Botón para realizar la recarga
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
    AppTheme(darkTheme = false) {
        DepositMoneyScreen(onNavigateBack = {})
    }
}