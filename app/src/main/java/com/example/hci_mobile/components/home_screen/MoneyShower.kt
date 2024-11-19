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
import androidx.compose.ui.unit.sp
import com.example.hci_mobile.ui.theme.AppTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.IconButton
import androidx.lint.kotlin.metadata.Visibility

@Composable
fun MoneyShower(
    modifier: Modifier = Modifier
){
    var isMoneyVisible by remember { mutableStateOf(true) }
    val availableMoney = 150000
    val gain = 37.2

    Surface(
        color = AppTheme.colorScheme.secondary,
        modifier = modifier,
        shape = AppTheme.shape.container
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Disponible", 
                    color = AppTheme.colorScheme.textColor, 
                    style = AppTheme.typography.body
                )
                Text(
                    text = "+$gain% (TNA)",
                    color = Color(0xFF4CAF50), // Color verde
                    style = AppTheme.typography.body,
                    fontSize = 14.sp
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = if (isMoneyVisible) "$ $availableMoney" else "$ ****",
                            color = AppTheme.colorScheme.textColor, 
                            style = AppTheme.typography.title.copy(
                                fontSize = 28.sp
                            ),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        IconButton(
                            onClick = { isMoneyVisible = !isMoneyVisible }
                        ) {
                            Icon(
                                imageVector = if (isMoneyVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (isMoneyVisible) "Ocultar saldo" else "Mostrar saldo",
                                tint = AppTheme.colorScheme.textColor
                            )
                        }
                    }
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