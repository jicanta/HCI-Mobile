package com.example.hci_mobile.components.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun Tile(
    dataIcon: DataIcon
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(65.dp)
                .background(
                    color = AppTheme.colorScheme.primary,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = dataIcon.icon,
                contentDescription = null,
                modifier = Modifier.size(45.dp),
                tint = AppTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = stringResource(id = dataIcon.label),
            style = AppTheme.typography.body,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TilePreview() {
    AppTheme(darkTheme = false) {
        Tile(dataIcon = DataIcon.DEPOSIT)
    }
}