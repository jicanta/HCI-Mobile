package com.example.hci_mobile.components.navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import com.example.hci_mobile.components.bottom_bar.BottomBar

import com.example.hci_mobile.ui.theme.AppTheme
@Composable
fun ResponsiveNavigation(
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val items = listOf(
        AppDestinations.HOME,
        AppDestinations.MOVEMENTS,
        AppDestinations.CARDS,
        AppDestinations.OPTIONS
    )

    if (isTablet && isLandscape) {
        NavigationRail(
            modifier = modifier
                .fillMaxHeight()
                .width(80.dp)
                .shadow(8.dp),
            containerColor = AppTheme.colorScheme.secondary
        ) {
            Spacer(modifier = Modifier.weight(1f))
            
            items.forEach { item ->
                NavigationRailItem(
                    selected = currentRoute == item.route,
                    onClick = { onNavigateToRoute(item.route) },
                    icon = {
                        item.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = stringResource(item.label),
                                modifier = Modifier.size(36.dp),
                                tint = if (currentRoute == item.route)
                                    AppTheme.colorScheme.tertiary
                                else
                                    AppTheme.colorScheme.textColor
                            )
                        }
                    },
                    colors = NavigationRailItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    } else {
        BottomBar(
            currentRoute = currentRoute,
            onNavigateToRoute = onNavigateToRoute
        )
    }
}