package com.example.hci_mobile.components.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hci_mobile.R

enum class AppDestinations(
    @StringRes val label: Int,
    val icon: ImageVector?,
    val route: String,
    val isTab: Boolean = false
){
    HOME(R.string.home, Icons.Outlined.Home, "home", true),
    MOVEMENTS(R.string.movements, Icons.Filled.History, "movements", true),
    CARDS(R.string.cards, Icons.Filled.CreditCard, "cards", true),
    OPTIONS(R.string.options, Icons.Filled.Menu, "options", true),
    DATA(R.string.data, Icons.Default.AccountCircle, "data"),
    DEPOSIT(R.string.deposit, Icons.Default.Add, "deposit"),
    LOGIN(R.string.login, null, "login"),
    ADDCARD(R.string.add_card, null, "addCard"),
    REGISTER(R.string.register, null, "register"),
    SEND(R.string.send, Icons.Default.ArrowForward, "send");

    companion object {
        fun isTabRoute(route: String): Boolean {
            return values().find { it.route == route }?.isTab ?: false
        }
    }
}