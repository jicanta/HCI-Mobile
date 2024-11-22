package com.example.hci_mobile.components.PaymentMethods
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hci_mobile.R
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentMethodScreen(
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopBarWithBack(title = R.string.add_payment_method, onNavigateBack = onNavigateBack)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Campo para el nombre del propietario
                CustomOutlinedTextField(
                    label = "Nombre del propietario",
                    keyboardType = KeyboardType.Text,
                    placeholder = "Como figura en la tarjeta"
                )

                // Campo para el número de tarjeta
                CustomOutlinedTextField(
                    label = "Número de tarjeta",
                    keyboardType = KeyboardType.Number,
                    placeholder = "XXXX-XXXX-XXXX-XXXX"
                )

                // Campo para la fecha de vencimiento
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomOutlinedTextField(
                        label = "Mes de vto.",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CustomOutlinedTextField(
                        label = "Año de vto.",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CustomOutlinedTextField(
                        label = "Cód. seguridad",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1f),
                        visualTransformation = VisualTransformation.None
                    )
                }

                // Botón Guardar
                Button(
                    onClick = { /* Acción al guardar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primary),
                    shape = AppTheme.shape.button,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text("Guardar", color = AppTheme.colorScheme.secondary)
                }
            }
        }
    )
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
