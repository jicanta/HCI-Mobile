package com.example.hci_mobile.components.PaymentMethods
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.R
import com.example.hci_mobile.api.data.model.CardType
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.example.hci_mobile.components.home_screen.getCardColor
import com.example.hci_mobile.components.home_screen.getCardIcon
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

// Función para formatear el número de tarjeta
private fun formatCardNumber(number: String): String {
    val digitsOnly = number.filter { it.isDigit() }
    val groups = mutableListOf<String>()
    
    var currentIndex = 0
    while (currentIndex < digitsOnly.length && currentIndex < 16) {
        val endIndex = minOf(currentIndex + 4, digitsOnly.length)
        groups.add(digitsOnly.substring(currentIndex, endIndex))
        currentIndex += 4
    }
    
    return groups.joinToString(" ")
}

// Función para validar que solo contenga letras y espacios
private fun isValidName(text: String): Boolean {
    return text.all { it.isLetter() || it.isWhitespace() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentMethodScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    var cardType by remember { mutableStateOf("") }
    var displayCardType by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var ownerName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expirationMonth by remember { mutableStateOf("") }
    var expirationYear by remember { mutableStateOf("") }
    var securityCode by remember { mutableStateOf("") }
    var cardNumberField by remember { mutableStateOf(TextFieldValue()) }
    
    // Variable para ir construyendo la tarjeta
    var card by remember { mutableStateOf(
        com.example.hci_mobile.api.data.model.Card(
            number = "",
            expirationDate = "",
            fullName = "",
            cvv = "",
            type = CardType.CREDIT
        )
    )}

    // Actualizar la tarjeta cuando cambian los valores
    card = com.example.hci_mobile.api.data.model.Card(
        number = cardNumber,
        expirationDate = if (expirationMonth.isNotEmpty() && expirationYear.isNotEmpty()) 
                        "$expirationMonth/$expirationYear" else "",
        fullName = ownerName,
        cvv = securityCode,
        type = when (cardType) {
            "CREDIT" -> CardType.CREDIT
            "DEBIT" -> CardType.DEBIT
            else -> CardType.CREDIT
        }
    )

    Scaffold(
        topBar = {
            TopBarWithBack(title = R.string.add_payment_method, onNavigateBack = onNavigateBack)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Preview Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 24.dp),
                    shape = AppTheme.shape.container,
                    colors = CardDefaults.cardColors(
                        containerColor = if (cardNumber.isEmpty()) AppTheme.colorScheme.primary 
                                        else getCardColor(getCardType(cardNumber))
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Card Type Icon
                        Icon(
                            painter = painterResource(
                                id = if (cardNumber.isEmpty()) R.drawable.paygo 
                                     else getCardIcon(getCardType(cardNumber))
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.TopEnd),
                            tint = Color.Unspecified
                        )

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Card Number
                            Text(
                                text = if (cardNumberField.text.isEmpty()) "•••• •••• •••• ••••" 
                                      else cardNumberField.text,
                                style = AppTheme.typography.title.copy(
                                    fontSize = 24.sp,
                                    color = AppTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.padding(top = 40.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Card Holder Name
                                Column {
                                    Text(
                                        text = stringResource(R.string.owner_name),
                                        style = AppTheme.typography.body.copy(
                                            color = AppTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                        )
                                    )
                                    Text(
                                        text = if (ownerName.isEmpty()) "CARD HOLDER" else ownerName.uppercase(),
                                        style = AppTheme.typography.body.copy(
                                            color = AppTheme.colorScheme.onPrimary
                                        )
                                    )
                                }

                                // Expiration Date
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "VALID THRU",
                                        style = AppTheme.typography.body.copy(
                                            color = AppTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                        )
                                    )
                                    Text(
                                        text = if (expirationMonth.isEmpty() || expirationYear.isEmpty()) 
                                              "MM/YY" else "$expirationMonth/$expirationYear",
                                        style = AppTheme.typography.body.copy(
                                            color = AppTheme.colorScheme.onPrimary
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Input Form Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        // Tipo de tarjeta (Dropdown)
                        ExposedDropdownMenuBox(
                            expanded = isDropdownExpanded,
                            onExpandedChange = { isDropdownExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = displayCardType,
                                onValueChange = { },
                                readOnly = true,
                                label = { Text(
                                    text = stringResource(id = R.string.card_type),
                                    style = AppTheme.typography.body
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
                                    unfocusedTextColor = AppTheme.colorScheme.textColor
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = isDropdownExpanded,
                                onDismissRequest = { isDropdownExpanded = false }
                            ) {
                                listOf(
                                    CardType.CREDIT to stringResource(R.string.credit),
                                    CardType.DEBIT to stringResource(R.string.debit)
                                ).forEach { (type, label) ->
                                    DropdownMenuItem(
                                        text = { 
                                            Text(
                                                text = label,
                                                style = AppTheme.typography.body
                                            )
                                        },
                                        onClick = {
                                            cardType = type.name
                                            displayCardType = label
                                            isDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Nombre del propietario (solo letras)
                        OutlinedTextField(
                            value = ownerName,
                            onValueChange = { newValue ->
                                if (isValidName(newValue)) {
                                    ownerName = newValue
                                }
                            },
                            label = { Text(stringResource(R.string.owner_name), style = AppTheme.typography.body) },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = AppTheme.colorScheme.onTertiary,
                                unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                                disabledContainerColor = AppTheme.colorScheme.onTertiary,
                                focusedTextColor = AppTheme.colorScheme.textColor,
                                unfocusedTextColor = AppTheme.colorScheme.textColor
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Número de tarjeta (solo números)
                        OutlinedTextField(
                            value = cardNumberField,
                            onValueChange = { newValue ->
                                val cleanText = newValue.text.replace(" ", "")
                                if (cleanText.all { it.isDigit() } && cleanText.length <= 16) {
                                    cardNumber = cleanText
                                    val formatted = formatCardNumber(cleanText)
                                    
                                    // Calculate new cursor position
                                    val cursorOffset = newValue.selection.start
                                    val previousSpaces = cardNumberField.text.take(cursorOffset).count { it == ' ' }
                                    val newSpaces = formatted.take(cursorOffset).count { it == ' ' }
                                    val cursorShift = newSpaces - previousSpaces
                                    
                                    cardNumberField = TextFieldValue(
                                        text = formatted,
                                        selection = TextRange(newValue.selection.start + cursorShift)
                                    )
                                }
                            },
                            label = { Text(stringResource(R.string.card_number), style = AppTheme.typography.body) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = AppTheme.colorScheme.onTertiary,
                                unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                                disabledContainerColor = AppTheme.colorScheme.onTertiary,
                                focusedTextColor = AppTheme.colorScheme.textColor,
                                unfocusedTextColor = AppTheme.colorScheme.textColor
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Fecha de vencimiento y código de seguridad
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedTextField(
                                value = expirationMonth,
                                onValueChange = { newValue ->
                                    if (newValue.all { it.isDigit() } && newValue.length <= 2) {
                                        expirationMonth = newValue
                                    }
                                },
                                label = { Text(stringResource(R.string.month), style = AppTheme.typography.body) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                                    disabledContainerColor = AppTheme.colorScheme.onTertiary,
                                    focusedTextColor = AppTheme.colorScheme.textColor,
                                    unfocusedTextColor = AppTheme.colorScheme.textColor
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                value = expirationYear,
                                onValueChange = { newValue ->
                                    if (newValue.all { it.isDigit() } && newValue.length <= 2) {
                                        expirationYear = newValue
                                    }
                                },
                                label = { Text(stringResource(R.string.year), style = AppTheme.typography.body) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                                    disabledContainerColor = AppTheme.colorScheme.onTertiary,
                                    focusedTextColor = AppTheme.colorScheme.textColor,
                                    unfocusedTextColor = AppTheme.colorScheme.textColor
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                value = securityCode,
                                onValueChange = { newValue ->
                                    if (newValue.all { it.isDigit() } && newValue.length <= 3) {
                                        securityCode = newValue
                                    }
                                },
                                label = { Text(stringResource(R.string.cvv), style = AppTheme.typography.body) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                                    disabledContainerColor = AppTheme.colorScheme.onTertiary,
                                    focusedTextColor = AppTheme.colorScheme.textColor,
                                    unfocusedTextColor = AppTheme.colorScheme.textColor
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Botón Guardar
                        Button(
                            onClick = {
                                viewModel.addCard(card)
                                onNavigateBack()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primary),
                            shape = AppTheme.shape.button,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(top = 24.dp),
                            enabled = cardType.isNotEmpty() &&
                                    ownerName.isNotEmpty() &&
                                    cardNumber.length == 16 &&
                                    expirationMonth.isNotEmpty() &&
                                    expirationYear.isNotEmpty() &&
                                    securityCode.length == 3
                        ) {
                            Text(
                                text = stringResource(R.string.save),
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
    }
}

@Preview(showBackground = true)
@Composable
fun AddPaymentMethodScreenPreview() {
    AppTheme(darkTheme = false){
        AddPaymentMethodScreen()
    }
}

@Composable
fun CustomOutlinedTextField(
    label: String,
    keyboardType: KeyboardType,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppTheme.colorScheme.primary,
            unfocusedBorderColor = AppTheme.colorScheme.background,
            focusedLabelColor = AppTheme.colorScheme.primary,
            cursorColor = AppTheme.colorScheme.primary
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
