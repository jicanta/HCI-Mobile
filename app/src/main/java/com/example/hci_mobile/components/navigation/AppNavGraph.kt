package com.example.hci_mobile.components.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.components.PaymentMethods.AddPaymentMethodScreen
import com.example.hci_mobile.components.PaymentMethods.PaymentMethodsScreen
import com.example.hci_mobile.components.deposit_money.DepositMoneyScreen
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.home_screen.HomeScreen
import com.example.hci_mobile.components.login.LoginScreen
import com.example.hci_mobile.components.more.SettingsScreen
import com.example.hci_mobile.components.movements.MovementsScreen
import com.example.hci_mobile.components.my_data.AccountDataScreen
import com.example.hci_mobile.components.register.RegisterScreen
import com.example.hci_mobile.components.send.SendScreen
import com.example.hci_mobile.components.verify.VerifyScreen
import java.util.Locale

@Composable
fun AppNavGraph(
    navController: NavHostController,
    darkTheme: Boolean,
    onThemeUpdated: (Boolean) -> Unit,
    currentLocale: Locale,
    onLanguageChanged: (Locale) -> Unit,
    onNavigateToRoute: (String) -> Unit = {},
    currentRoute: String? = null,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
) {
    val uiState = viewModel.uiState

    val onNavigateBack: () -> Unit = {
        navController.popBackStack()
    }

    var startDestination = AppDestinations.LOGIN.route

    if(uiState.isAuthenticated){
        startDestination = AppDestinations.HOME.route
    }


    NavHost(
        navController = navController,
        startDestination = startDestination,
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
            DepositMoneyScreen(
                onNavigateBack = onNavigateBack
            )
        }
        composable(route = AppDestinations.LOGIN.route) {
            LoginScreen(onNavigateToRoute = onNavigateToRoute)
        }
        composable(route = AppDestinations.ADDCARD.route) {
            AddPaymentMethodScreen(onNavigateBack = onNavigateBack)
        }
        composable(route = AppDestinations.REGISTER.route) {
            RegisterScreen(onNavigateToRoute = onNavigateToRoute)
        }
        composable(route = AppDestinations.SEND.route) {
            SendScreen(onNavigateBack = onNavigateBack, onNavigateToRoute = onNavigateToRoute)
        }
        composable(route = AppDestinations.VERIFY.route) {
            VerifyScreen(onNavigateToRoute = onNavigateToRoute)
        }


    }
}