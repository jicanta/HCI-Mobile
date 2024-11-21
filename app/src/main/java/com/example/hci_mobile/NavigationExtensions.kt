package com.example.hci_mobile

import androidx.navigation.NavHostController

fun NavHostController.navigateToRoute(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
} 