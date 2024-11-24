package com.example.hci_mobile.components.verticalBar

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
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
fun VerticalBar(
    currentRoute: String? = null,
    onNavigateToRoute: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val items = listOf(
        AppDestinations.HOME,
        AppDestinations.MOVEMENTS,
        AppDestinations.CARDS,
        AppDestinations.OPTIONS
    )
    NavigationRail(
        containerColor = AppTheme.colorScheme.secondary,
        modifier = modifier
            .fillMaxHeight()
            .width(80.dp)
            .shadow(8.dp)
    ) {
        items.forEach { item ->
            NavigationRailItem(
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
                colors = NavigationRailItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VerticalBarPreview() {
    AppTheme(darkTheme = false) {
        VerticalBar(
            currentRoute = AppDestinations.HOME.route,
            onNavigateToRoute = {}
        )
    }
}
