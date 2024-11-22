package com.example.hci_mobile.components.invest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hci_mobile.R
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopBarWithBack(
                title = R.string.investment, // Título de la pantalla desde los recursos
                onNavigateBack = onNavigateBack // Acción para navegar hacia atrás
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Padding del scaffold
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Sección: Introducción
            InvestmentHeader()

            Spacer(modifier = Modifier.height(16.dp))

            // Sección: Inversión actual
            CurrentInvestmentSection()

            Spacer(modifier = Modifier.height(16.dp))

            // Sección: Invertir
            InvestmentActionSection(
                title = stringResource(id = R.string.invest),
                inputHint = stringResource(id = R.string.amount_to_invest),
                available = stringResource(id = R.string.available_amount, "$3000.00"),
                buttonText = stringResource(id = R.string.invest)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sección: Rescate
            InvestmentActionSection(
                title = stringResource(id = R.string.rescue),
                inputHint = stringResource(id = R.string.amount_to_rescue),
                available = stringResource(id = R.string.available_amount, "$8442.56"),
                buttonText = stringResource(id = R.string.rescue)
            )
        }
    }
}

@Preview
@Composable
fun InvestmentScreenPreview() {
    AppTheme(darkTheme = false) {
        InvestmentScreen(onNavigateBack = {})
    }
}

@Composable
fun InvestmentHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.investment_intro_title),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.investment_intro_description),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CurrentInvestmentSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.invested),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$8,442.56",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(id = R.string.tna, "37.2%"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun InvestmentActionSection(
    title: String,
    inputHint: String,
    available: String,
    buttonText: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = inputHint) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = available,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Acción del botón */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = buttonText)
            }
        }
    }
}
