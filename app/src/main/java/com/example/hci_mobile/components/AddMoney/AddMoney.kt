package com.example.hci_mobile.components.AddMoney
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.R
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMoneyScreen(
    onNavigateBack: () -> Unit,
    cards: List<String> // Lista de tarjetas de crédito disponibles
) {
    var selectedCard by remember { mutableStateOf(cards.firstOrNull() ?: "") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = R.string.add_money, // Título desde strings.xml
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Campo de texto para el monto
            Text(
                text = stringResource(id = R.string.amount),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text(text = stringResource(id = R.string.enter_amount)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Campo de texto para la descripción
            Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text = stringResource(id = R.string.enter_description)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))


            // Sección para seleccionar la tarjeta de crédito
            Text(
                text = stringResource(id = R.string.select_card),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = it }
            ) {
                TextField(
                    value = selectedCard,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text(text = stringResource(R.string.selected_card)) },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    cards.forEach { card ->
                        DropdownMenuItem(
                            text = { Text(text = card) },
                            onClick = {
                                selectedCard = card
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            // Botón para confirmar
            Button(
                onClick = {
                    // Acción al presionar el botón
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.continuar))
            }
        }
    }
}

@Preview
@Composable
fun AddMoneyScreenPreview() {
    val cards = listOf("Tarjeta 1", "Tarjeta 2", "Tarjeta 3")
    AppTheme(darkTheme = false) {
        AddMoneyScreen(onNavigateBack = {}, cards = cards)
    }
}
