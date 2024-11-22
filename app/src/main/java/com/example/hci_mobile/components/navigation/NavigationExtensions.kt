package com.example.hci_mobile.components.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToRoute(route: String) {
    if (AppDestinations.isTabRoute(route)) {
        navigate(route) {
            popUpTo(graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    } else {
        navigate(route) {
            launchSingleTop = true
        }
    }
} 