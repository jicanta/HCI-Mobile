package com.example.hci_mobile.components.home_screen

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hci_mobile.ui.theme.AppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardContainer(
    modifier: Modifier = Modifier,
){
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = AppTheme.shape.container,
        color = AppTheme.colorScheme.secondary
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Medios de Pago",
                style = AppTheme.typography.title,
                color = AppTheme.colorScheme.textColor,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            SmallCard(
                number = "**** 4321",
                type = "Visa",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            SmallCard(
                number = "**** 8765",
                type = "Mastercard",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            SmallCard(
                number = "**** 9876",
                type = "American Express"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardContainerPreview() {
    AppTheme {
        CardContainer()
    }
}