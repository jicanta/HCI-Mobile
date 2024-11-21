package com.example.hci_mobile.components.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hci_mobile.R

enum class AppDestinations(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String
){
    HOME(R.string.home, Icons.Outlined.Home, "home"),
    MOVEMENTS(R.string.movements, Icons.Filled.History, "movements"),
    CARDS(R.string.cards, Icons.Filled.CreditCard, "cards"),
    OPTIONS(R.string.options, Icons.Filled.Menu, "options"),
    QR(R.string.qr, Icons.Filled.Menu, "qr")

}