package com.example.hci_mobile.components.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
import com.example.hci_mobile.R
import java.text.NumberFormat
import androidx.compose.ui.platform.LocalContext

@Composable
fun MoneyDisplay(
    modifier: Modifier = Modifier,
    availableMoney: Double = 0.0
){
    var isMoneyVisible by remember { mutableStateOf(true) }

    val locale = LocalContext.current.resources.configuration.locales[0]
    val numberFormatter = NumberFormat.getNumberInstance(locale).apply {
        maximumFractionDigits = 2
        minimumFractionDigits = 2
    }
    val formattedMoney = numberFormatter.format(availableMoney)

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
                    text = stringResource(R.string.available),
                    color = AppTheme.colorScheme.textColor, 
                    style = AppTheme.typography.body
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
                            text = if (isMoneyVisible) 
                                stringResource(R.string.available_money, formattedMoney)
                            else 
                                stringResource(R.string.hidden_money),
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
                                imageVector = if (isMoneyVisible) 
                                    Icons.Default.Visibility 
                                else 
                                    Icons.Default.VisibilityOff,
                                contentDescription = stringResource(
                                    if (isMoneyVisible) 
                                        R.string.hide_balance 
                                    else 
                                        R.string.show_balance
                                ),
                                tint = AppTheme.colorScheme.textColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoneyDisplayPreview(){
    AppTheme(darkTheme = false){
        MoneyDisplay()
    }
}