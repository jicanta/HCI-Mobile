package com.example.hci_mobile.components.movements

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.R
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme
import com.example.hci_mobile.api.data.model.Movement
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import com.example.hci_mobile.components.navigation.ResponsiveNavigation

@Composable
fun MovementsScreen(
    modifier: Modifier = Modifier,
    currentRoute: String? = null,
    onNavigateToRoute: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        viewModel.getPayments()
    }

    Log.d("MovementsScreen", "Payments: ${uiState.payments?.size}")

    Scaffold(
        containerColor = AppTheme.colorScheme.background,
        topBar = { TopBarWithBack(R.string.movements, onNavigateBack = onNavigateBack) },
        bottomBar = {
            if (!isTablet || !isLandscape) {
                ResponsiveNavigation(
                    currentRoute = currentRoute,
                    onNavigateToRoute = onNavigateToRoute,
                    modifier = Modifier
                )
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isTablet && isLandscape) {
                ResponsiveNavigation(
                    currentRoute = currentRoute,
                    onNavigateToRoute = onNavigateToRoute,
                    modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
                )
            }
            
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                if (uiState.payments.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.available_movements),
                            style = AppTheme.typography.body,
                            color = AppTheme.colorScheme.textColor
                        )
                    }
                } else {
                    MovementList(movements = uiState.payments)
                }
            }
        }
    }
}

@Composable
fun MovementList(movements: List<Movement>) {
    val itemsPerPage = 10
    var currentPage by remember { mutableStateOf(0) }
    val pageCount = (movements.size + itemsPerPage - 1) / itemsPerPage

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            val currentPageItems = movements.chunked(itemsPerPage)[currentPage]
            
            items(currentPageItems.size) { index ->
                val movement = currentPageItems[index]
                Text(
                    text = SimpleDateFormat("dd 'de' MMMM", Locale("es")).format(movement.createdAt),
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.textColor,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )
                MovementItem(movement)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Paginación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until pageCount) {
                TextButton(
                    onClick = { currentPage = i },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (currentPage == i) 
                            AppTheme.colorScheme.primary 
                        else 
                            AppTheme.colorScheme.textColor
                    )
                ) {
                    Text(
                        text = (i + 1).toString(),
                        style = AppTheme.typography.body,
                        fontWeight = if (currentPage == i) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
fun MovementItem(movement: Movement) {
    Card(
        shape = AppTheme.shape.container,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null,
                tint = AppTheme.colorScheme.textColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Nombre: ${movement.receiver.firstName} ${movement.receiver.lastName}",
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.tertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movement.receiver.email,
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = "$ ${movement.amount}",
                style = AppTheme.typography.body,
                fontWeight = FontWeight.Bold,
                color = if (movement.amount > 0) Color.Green else Color.Red
            )
        }
    }
}
