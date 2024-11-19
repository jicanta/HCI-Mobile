package com.example.hci_mobile.components.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun MoneyShower(
    modifier: Modifier = Modifier
){
    Surface(
        color = AppTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp), // Ocupa todo el ancho
            horizontalAlignment = Alignment.Start
        ) {
            Text("Disponible", color = AppTheme.colorScheme.textColor)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("$1000", color = AppTheme.colorScheme.textColor)
                }
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    /*Icon(
                        painter = painterResource(R.drawable.estudio),
                        contentDescription = null,
                        Modifier.size(40.dp)
                    )*/
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoneyShowerPreview(){
    AppTheme(darkTheme = false){
        MoneyShower()
    }
}