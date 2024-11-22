package com.example.hci_mobile.components.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hci_mobile.components.PaymentMethods.AddPaymentMethodScreen
import com.example.hci_mobile.components.PaymentMethods.PaymentMethodsScreen
import com.example.hci_mobile.components.home_screen.HomeScreen
import com.example.hci_mobile.components.login.LoginScreen
import com.example.hci_mobile.components.more.SettingsScreen
import com.example.hci_mobile.components.movements.MovementsScreen
import com.example.hci_mobile.components.my_data.AccountDataScreen
import java.util.Locale

@Composable
fun AppNavGraph(
    navController: NavHostController,
    darkTheme: Boolean,
    onThemeUpdated: (Boolean) -> Unit,
    currentLocale: Locale,
    onLanguageChanged: (Locale) -> Unit,
    onNavigateToRoute: (String) -> Unit = {},
    currentRoute: String? = null
) {
    // Definir la función de navegación hacia atrás
    val onNavigateBack: () -> Unit = {
        navController.popBackStack()
    }

    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route,
        modifier = Modifier
    ) {
        composable(route = AppDestinations.HOME.route) {
            HomeScreen(
                onNavigateToRoute = onNavigateToRoute, 
                currentRoute = currentRoute
            )
        }
        composable(route = AppDestinations.MOVEMENTS.route) {
            MovementsScreen(
                currentRoute = currentRoute,
                onNavigateToRoute = onNavigateToRoute,
                onNavigateBack = onNavigateBack
            )
        }
        composable(route = AppDestinations.CARDS.route) {
            PaymentMethodsScreen(
                currentRoute = currentRoute, 
                onNavigateToRoute = onNavigateToRoute,
                onNavigateBack = onNavigateBack
            )
        }
        composable(route = AppDestinations.OPTIONS.route) {
            SettingsScreen(
                darkTheme = darkTheme,
                onThemeUpdated = onThemeUpdated,
                currentLocale = currentLocale,
                onLanguageChanged = onLanguageChanged,
                currentRoute = currentRoute,
                onNavigateToRoute = onNavigateToRoute,
                onNavigateBack = onNavigateBack
            )
        }
        composable(route = AppDestinations.DATA.route) {
            AccountDataScreen(onNavigateBack = onNavigateBack)
        }
        composable(route = AppDestinations.DEPOSIT.route) {
            Text(text = "Deposit")
        }
        composable(route = AppDestinations.LOGIN.route) {
            LoginScreen()
        }
        composable(route = AppDestinations.ADDCARD.route) {
            AddPaymentMethodScreen(onNavigateBack = onNavigateBack)
        }
        composable(route = AppDestinations.REGISTER.route) {
            Text(text = "Register")
        }
        composable(route = AppDestinations.SEND.route) {
            Text(text = "Send")
        }
    }
}