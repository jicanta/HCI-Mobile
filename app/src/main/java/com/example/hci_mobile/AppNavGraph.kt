package com.example.hci_mobile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hci_mobile.components.home_screen.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route,
        modifier = Modifier.padding(padding)
    )
    {
        composable(route = AppDestinations.HOME.route) {
            HomeScreen()
        }
        composable(route = AppDestinations.MOVEMENTS.route) {
            Text("movements")
        }
        composable(route = AppDestinations.CARDS.route) {
            Text("cards")
        }
        composable(route = AppDestinations.OPTIONS.route) {
            Text("options")
        }
        composable(route = AppDestinations.QR.route) {
            Text("qr")
        }
    }
}