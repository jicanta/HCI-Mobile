package com.example.hci_mobile.components.PaymentMethods

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hci_mobile.R
import com.example.hci_mobile.components.home_screen.getCardColor
import com.example.hci_mobile.components.home_screen.getCardIcon
import com.example.hci_mobile.ui.theme.AppTheme


@Composable
fun PaymentMethodsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        PaymentMethodList()

        AddPaymentMethodButton()
    }
}
@Preview
@Composable
fun PaymentMethodsScreenPreview() {
    PaymentMethodsScreen()
}

@Composable
fun TopBar(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5A2A82)) // Color púrpura
            .padding(vertical = 16.dp), // Padding interno
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp) // Espaciado del texto
        )
    }
}

@Composable
fun PaymentMethodList() {
    val paymentMethods = listOf(
        PaymentMethod("Mastercard", "Birsa Juan Pablo", "4444"),
        PaymentMethod("Visa", "Birsa Juan Pablo", "4444"),
        PaymentMethod("American Express", "Birsa Juan Pablo", "4444")
    )
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = AppTheme.shape.container,
        color = AppTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            paymentMethods.forEach { paymentMethod ->
                PaymentMethodItem(paymentMethod)
                if (paymentMethod != paymentMethods.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun PaymentMethodItem(paymentMethod: PaymentMethod) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(getCardColor(type = paymentMethod.type), AppTheme.shape.container)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = getCardIcon(type = paymentMethod.type)),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "•••• ${paymentMethod.lastFourDigits}",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                style = AppTheme.typography.body
            )
            Text(
                text = paymentMethod.ownerName,
                color = Color.White,
                fontSize = 14.sp,
                style = AppTheme.typography.body
            )
        }
        IconButton(onClick = { /* Acción para eliminar */ }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar método de pago",
                tint = Color.White
            )
        }
    }
}

@Composable
fun AddPaymentMethodButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .background(AppTheme.colorScheme.background, AppTheme.shape.container)
            .border(2.dp, Color(0xFFFF4081), AppTheme.shape.container)
            .clickable { /* onClick -> HAGO ALGO */ }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Añadir método de pago",
                tint = AppTheme.colorScheme.tertiary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.add_payment_method),
                color = AppTheme.colorScheme.tertiary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                style = AppTheme.typography.body
            )
        }
    }
}

data class PaymentMethod(
    val type: String,
    val ownerName: String,
    val lastFourDigits: String
)
