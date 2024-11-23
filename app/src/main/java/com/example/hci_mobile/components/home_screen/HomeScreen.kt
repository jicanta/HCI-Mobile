package com.example.hci_mobile.components.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.ui.theme.AppTheme
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.homeApi.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToRoute: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
    currentRoute: String? = null
){
    val uiState = viewModel.uiState

    viewModel.getBalance()

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
            CardContainer(modifier = Modifier.padding(top = 16.dp))
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