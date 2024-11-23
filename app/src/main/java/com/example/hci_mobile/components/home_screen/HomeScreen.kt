package com.example.hci_mobile.components.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.ui.theme.AppTheme
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.homeApi.HomeViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.clickable
import androidx.compose.material3.Scaffold
import com.example.hci_mobile.R
import com.example.hci_mobile.components.PaymentMethods.getCardType
import com.example.hci_mobile.components.home_screen.SmallCard
import com.example.hci_mobile.components.navigation.AppDestinations

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToRoute: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
    currentRoute: String? = null
){
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getBalance()
        while(true) {
            delay(60000) // 1 minuto
            viewModel.getBalance()
        }
    }

    Scaffold(
        containerColor = AppTheme.colorScheme.background,
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(
                currentRoute = currentRoute,
                onNavigateToRoute = onNavigateToRoute
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.balance?.balance?.let {
                MoneyDisplay(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .shadow(shape = AppTheme.shape.container, elevation = 4.dp),
                    availableMoney = it
                )
            }
            TileRow(
                modifier = Modifier.padding(top = 16.dp),
                onNavigateToRoute = onNavigateToRoute
            )
            CardContainer(modifier = Modifier.padding(top = 16.dp), onNavigateToRoute = onNavigateToRoute)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    AppTheme(darkTheme = false){
        //HomeScreen()
    }
}

@Composable
fun CardContainer(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
    onNavigateToRoute: (String) -> Unit
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getCards()
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = AppTheme.shape.container,
        color = AppTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.payment_methods),
                style = AppTheme.typography.title,
                color = AppTheme.colorScheme.textColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Mostrar las tarjetas usando SmallCard
            uiState.cards?.take(3)?.forEach { card ->
                SmallCard(
                    number = "****-${card.number.takeLast(4)}",
                    type = getCardType(card.number),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Text(
                text = stringResource(R.string.view_all_payment_methods),
                style = AppTheme.typography.body.copy(
                    color = AppTheme.colorScheme.tertiary
                ),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onNavigateToRoute(AppDestinations.CARDS.route) }
            )
        }
    }
}