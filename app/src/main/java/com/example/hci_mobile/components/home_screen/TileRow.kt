package com.example.hci_mobile.components.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.components.home_screen.DataIcon
import com.example.hci_mobile.components.home_screen.Tile
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun TileRow(
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        DataIcon.values().forEach { dataIcon ->
            Tile(dataIcon = dataIcon)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TileRowPreview() {
    AppTheme(darkTheme = false) {
        TileRow(modifier = Modifier.padding(top = 16.dp))
    }
}