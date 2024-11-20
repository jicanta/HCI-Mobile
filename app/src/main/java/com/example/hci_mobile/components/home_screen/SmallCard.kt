package com.example.hci_mobile.components.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hci_mobile.ui.theme.AppTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.hci_mobile.R

@Composable
fun SmallCard(
    modifier: Modifier = Modifier,
    number: String,
    type: String,
){
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = AppTheme.shape.container,
        color = getCardColor(type)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = number,
                style = AppTheme.typography.body,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(id = getCardIcon(type)),
                contentDescription = "Ícono de $type",
                modifier = Modifier.size(36.dp),
                tint = Color.Unspecified
            )
        }
    }
}

fun getCardColor(type: String): Color {
    return when (type.lowercase()) {
        "visa" -> Color(0xFF000000)
        "mastercard" -> Color(0xFF2196F3)
        "american express" -> Color(0xFFEF6C00)
        else -> Color(0xFF9E9E9E)
    }
}

fun getCardIcon(type: String): Int {
    return when (type.lowercase()) {
        "visa" -> R.drawable.visa
        "mastercard" -> R.drawable.mastercard
        "american express" -> R.drawable.amex
        else -> R.drawable.paygo
    }
}

@Preview(showBackground = true)
@Composable
fun SmallCardPreview() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SmallCard(
                number = "**** 1234",
                type = "visa"
            )
            SmallCard(
                number = "**** 5678",
                type = "mastercard"
            )
            SmallCard(
                number = "**** 9012",
                type = "american express"
            )
        }
    }
}