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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentMethodScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Añadir método de pago", color = Color.White, fontSize = 18.sp)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5E2A84) // Color del TopAppBar
                )
            )
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E2A84)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text("Guardar", color = Color.White)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddPaymentMethodScreenPreview() {
    AddPaymentMethodScreen()
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
            focusedBorderColor = Color(0xFF5E2A84),
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color(0xFF5E2A84),
            cursorColor = Color(0xFF5E2A84)
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
