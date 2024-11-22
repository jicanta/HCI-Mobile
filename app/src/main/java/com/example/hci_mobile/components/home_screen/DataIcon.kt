package com.example.hci_mobile.components.home_screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hci_mobile.R
import com.example.hci_mobile.components.navigation.AppDestinations

enum class DataIcon(
    @StringRes val label: Int,
    val icon: ImageVector?,
    val route: String
) {
    DEPOSIT(R.string.deposit, AppDestinations.DEPOSIT.icon, AppDestinations.DEPOSIT.route),
    SEND(R.string.send, AppDestinations.SEND.icon, AppDestinations.SEND.route),
    MOVEMENTS(R.string.movements, AppDestinations.MOVEMENTS.icon, AppDestinations.MOVEMENTS.route),
    DATA(R.string.data, AppDestinations.DATA.icon, AppDestinations.DATA.route)
}