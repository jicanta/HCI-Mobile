package com.example.hci_mobile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hci_mobile.components.AppDestinations
import com.example.hci_mobile.components.PaymentMethods.PaymentMethodsScreen
import com.example.hci_mobile.components.home_screen.HomeScreen
import com.example.hci_mobile.components.more.SettingsScreen
import com.example.hci_mobile.components.movements.MovementsScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import java.util.Locale

@Composable
fun AppNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    darkTheme: Boolean,
    onThemeUpdated: (Boolean) -> Unit,
    currentLocale: Locale,
    onLanguageChanged: (Locale) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route,
        modifier = Modifier.padding(padding)
    )
    {
        composable(route = AppDestinations.HOME.route) {
            HomeScreen(navController = navController)
        }
        composable(route = AppDestinations.MOVEMENTS.route) {
            MovementsScreen()
        }
        composable(route = AppDestinations.CARDS.route) {
            PaymentMethodsScreen()
        }
        composable(route = AppDestinations.OPTIONS.route) {
            SettingsScreen(
                darkTheme = darkTheme,
                onThemeUpdated = onThemeUpdated,
                currentLocale = currentLocale,
                onLanguageChanged = onLanguageChanged
            )
        }
        composable(route = AppDestinations.QR.route) {
            Text("qr")
        }
    }
}