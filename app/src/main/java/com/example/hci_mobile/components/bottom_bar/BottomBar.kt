package com.example.hci_mobile.components.bottom_bar


import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.components.navigation.AppDestinations
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun BottomBar(
    currentRoute: String? = null,
    onNavigateToRoute: (String) -> Unit = {}
) {
    val items = listOf(
        AppDestinations.HOME,
        AppDestinations.MOVEMENTS,
        AppDestinations.CARDS,
        AppDestinations.OPTIONS
    )
    NavigationBar(
        containerColor = AppTheme.colorScheme.secondary,
        modifier = Modifier.shadow(8.dp)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    item.icon?.let {
                        Icon(
                            modifier = Modifier.size(36.dp),
                            imageVector = it,
                            contentDescription = stringResource(item.label),
                            tint = if (currentRoute == item.route)
                                AppTheme.colorScheme.tertiary
                            else
                                AppTheme.colorScheme.textColor
                        )
                    }
                },
                onClick = { onNavigateToRoute(item.route) },
                selected = currentRoute == item.route,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                alwaysShowLabel = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    AppTheme(darkTheme = false){
        BottomBar(
            currentRoute = AppDestinations.HOME.route,
            onNavigateToRoute = {}
        )
    }
}