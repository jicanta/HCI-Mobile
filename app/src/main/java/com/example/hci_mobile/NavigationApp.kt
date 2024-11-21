package com.example.hci_mobile

import android.view.Surface
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.ui.theme.AppTheme
import android.os.Build
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import java.util.Locale
import android.content.Context
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import android.content.Intent

// Creamos un CompositionLocal para el idioma
val LocalLanguage = compositionLocalOf { Locale.getDefault() }

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    
    // Obtener el tema del sistema y guardarlo si es la primera vez
    val systemInDarkTheme = isSystemInDarkTheme()
    val isFirstLaunch = sharedPrefs.getBoolean("is_first_launch", true)
    
    if (isFirstLaunch) {
        sharedPrefs.edit()
            .putBoolean("dark_theme", systemInDarkTheme)
            .putString("selected_language", context.resources.configuration.locales[0].language)
            .putBoolean("is_first_launch", false)
            .apply()
    }
    
    // Usar el tema guardado o el del sistema
    val savedDarkTheme = sharedPrefs.getBoolean("dark_theme", systemInDarkTheme)
    var darkTheme by remember { mutableStateOf(savedDarkTheme) }
    
    // Usar el idioma guardado o el del sistema
    val systemLocale = context.resources.configuration.locales[0]
    val savedLanguage = sharedPrefs.getString("selected_language", systemLocale.language)
    var currentLocale by remember { mutableStateOf(Locale(savedLanguage!!)) }

    AppTheme(darkTheme = darkTheme) {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = {
                BottomBar(
                    currentRoute = currentDestination,
                    onNavigateToRoute = { route -> 
                        navController.navigateToRoute(route)
                    }
                )
            }
        ) { paddingValues ->
            AppNavGraph(
                navController = navController, 
                padding = paddingValues,
                darkTheme = darkTheme,
                onThemeUpdated = { isDark -> 
                    darkTheme = isDark
                    sharedPrefs.edit()
                        .putBoolean("dark_theme", isDark)
                        .apply()
                },
                currentLocale = currentLocale,
                onLanguageChanged = { newLocale ->
                    sharedPrefs.edit()
                        .putString("selected_language", newLocale.language)
                        .apply()
                    
                    if (context is MainActivity) {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        context.finish()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationAppPreview() {
    AppTheme(darkTheme = false) {
        NavigationApp()
    }
}

// Función simple para obtener el idioma del sistema
fun getSystemLocale(context: Context): Locale {
    return context.resources.configuration.locales[0]
}

fun updateLocale(context: Context, locale: Locale) {
    // Guardamos la preferencia
    context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        .edit()
        .putString("selected_language", locale.language)
        .apply()
    
    // Reiniciamos la app solo una vez
    if (context is MainActivity) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        context.finish()
    }
}