package com.example.hci_mobile.components.paymentLink

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.R
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratePaymentLinkScreen(onNavigateBack: () -> Unit, onClickDetails: () -> Unit) {
    Scaffold(
        topBar = {
            TopBarWithBack(
                title = R.string.generate_payment_link, // Título de la pantalla desde los recursos
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
            // Card para generar link de pago
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.generate_payment_link_title),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text(text = stringResource(id = R.string.amount)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text(text = stringResource(id = R.string.description)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* Acción del botón Generar */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.generate))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nota informativa
            Text(
                text = stringResource(id = R.string.generate_payment_link_note),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Link "acá"
            val annotatedText = buildAnnotatedString {
                append(stringResource(id = R.string.link_note_prefix))
                append(" ")
                pushStringAnnotation(tag = "details", annotation = "details")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = R.string.here))
                }
                pop()
            }
            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    annotatedText.getStringAnnotations(tag = "details", start = offset, end = offset)
                        .firstOrNull()?.let {
                            onClickDetails()
                        }
                }
            )
        }
    }
}

@Preview
@Composable
fun GeneratePaymentLinkScreenPreview() {
    AppTheme(darkTheme = false) {
        GeneratePaymentLinkScreen(onNavigateBack = {}, onClickDetails = {})
    }
}

