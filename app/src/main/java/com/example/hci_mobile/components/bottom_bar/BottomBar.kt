package com.example.hci_mobile.components.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hci_mobile.components.AppDestinations
import com.example.hci_mobile.ui.theme.AppTheme
import kotlinx.coroutines.selects.select

@Composable
fun BottomBar(
    currentRoute: String? = null,
    onNavigateToRoute: (String) -> Unit = {}
) {
    val items = listOf(
        AppDestinations.HOME,
        AppDestinations.MOVEMENTS,
        AppDestinations.QR,
        AppDestinations.CARDS,
        AppDestinations.OPTIONS
    )
    NavigationBar(
        containerColor = AppTheme.colorScheme.primary
    ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(item.label),
                            tint = if (currentRoute == item.route) 
                                AppTheme.colorScheme.tertiary
                            else 
                                AppTheme.colorScheme.onPrimary
                        )
                    },
                    onClick = { onNavigateToRoute(item.route) },
                    selected = currentRoute == item.route,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AppTheme.colorScheme.secondary,
                        unselectedIconColor = AppTheme.colorScheme.onPrimary,
                        indicatorColor = AppTheme.colorScheme.primary
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