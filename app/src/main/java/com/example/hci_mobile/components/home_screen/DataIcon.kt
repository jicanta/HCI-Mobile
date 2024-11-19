package com.example.hci_mobile.components.home_screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hci_mobile.R

enum class DataIcon(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String
) {
    DEPOSIT(R.string.deposit, Icons.Default.Add, "deposit"),
    SEND(R.string.send, Icons.Default.ArrowForward, "send"),
    MOVEMENTS(R.string.movements, Icons.Default.Refresh, "movements"),
    DATA(R.string.data, Icons.Default.AccountCircle, "data")
}