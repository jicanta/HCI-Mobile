package com.example.hci_mobile.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hci_mobile.R

enum class AppDestinations(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String
){
    HOME(R.string.home, Icons.Filled.Home, "home"),
    MOVEMENTS(R.string.movements, Icons.Filled.Refresh, "movements"),
    CARDS(R.string.cards, Icons.Filled.MailOutline, "cards"),
    OPTIONS(R.string.options, Icons.Filled.Menu, "options"),
    QR(R.string.qr, Icons.Filled.Menu, "qr")

}