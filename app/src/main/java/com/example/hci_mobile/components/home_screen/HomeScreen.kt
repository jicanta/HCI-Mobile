package com.example.hci_mobile.components.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
    Surface(
        color = AppTheme.colorScheme.background
    ) {
        Column(modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MoneyShower(modifier
                .padding(bottom = 16.dp)
                .shadow(shape = AppTheme.shape.container, elevation = 4.dp)
            )
            TileRow(modifier = Modifier.padding(top = 16.dp))
            CardContainer(modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    AppTheme(darkTheme = false){
        HomeScreen()
    }
}