package com.example.hci_mobile.components.my_data
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hci_mobile.R
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun AccountDataScreen(
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = { TopBarWithBack(R.string.data, onNavigateBack = onNavigateBack) } // Usando tu implementación existente

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(R.color.light_gray)) // Fondo gris claro
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AccountCard()
        }
    }
}

@Composable
fun AccountCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AccountItem(label = stringResource(R.string.full_name), value = "Juan Pablo Birsa")
            AccountItem(label = stringResource(R.string.userName), value = "jpincha2009")
            AccountItem(
                label = "CVU",
                value = "0000003100049975085106",
                copyOption = true
            )
            AccountItem(
                label = "Alias",
                value = "estudiantes.campeon.mundial",
                copyOption = true
            )
            AccountItem(label = stringResource(R.string.phoneNumber), value = "+54 11 09122018")
        }
    }
}

@Composable
fun AccountItem(label: String, value: String, copyOption: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        if (copyOption) {
            TextButton(onClick = { /* Acción para copiar */ }) {
                Text(
                    text = stringResource(id = R.string.copy),
                    fontSize = 14.sp,
                    color = colorResource( id = R.color.purple_200)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountVerificationScreenPreview() {
    AppTheme(darkTheme = false) {
        AccountDataScreen()
    }
}
