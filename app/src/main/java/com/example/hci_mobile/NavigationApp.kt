package com.example.hci_mobile

import android.view.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.ui.theme.AppTheme


@Composable
fun NavigationApp(){

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
                TopBar()
                 },
        bottomBar = {
                BottomBar(
                    currentRoute = currentRoute
                ){
                        route -> navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                    }
                }
            }
        ){  //aca abajo esta el contenido de la pantalla
            AppNavGraph(navController = navController, padding = it)
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationAppPreview(){
    AppTheme(darkTheme = false){
        NavigationApp()
    }
}