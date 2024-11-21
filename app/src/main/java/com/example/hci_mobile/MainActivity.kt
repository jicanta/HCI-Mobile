package com.example.hci_mobile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.home_screen.HomeScreen
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.ui.theme.AppTheme
import com.example.hci_mobile.ui.theme.HCIMobileTheme
import androidx.compose.foundation.isSystemInDarkTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationApp()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val savedLanguage = newBase.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getString("selected_language", null)
        
        val locale = if (savedLanguage != null) {
            Locale(savedLanguage)
        } else {
            newBase.resources.configuration.locales[0]
        }
        
        val config = newBase.resources.configuration
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme(darkTheme = false) {
        NavigationApp()
    }
}