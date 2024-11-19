package com.example.hci_mobile.components.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hci_mobile.ui.theme.AppTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = number,
                style = AppTheme.typography.body,
                color = Color.White
            )
            Text(
                text = type,
                style = AppTheme.typography.body,
                color = Color.White
            )
        }
    }
}

fun getCardColor(type: String): Color {
    return when (type) {
        "Visa" -> Color(0xFF000000)
        "Mastercard" -> Color(0xFF2196F3)
        "American Express" -> Color(0xFFB1A78D)
        else -> Color(0xFF9E9E9E)
    }
}

@Preview(showBackground = true)
@Composable
fun SmallCardPreview() {
    AppTheme {
        SmallCard(
            number = "**** 1234",
            type = "American Express"
        )
    }
}