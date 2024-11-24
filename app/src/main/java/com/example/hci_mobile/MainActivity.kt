package com.example.hci_mobile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.hci_mobile.ui.theme.AppTheme
import com.example.hci_mobile.components.navigation.NavigationApp
import java.util.Locale

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