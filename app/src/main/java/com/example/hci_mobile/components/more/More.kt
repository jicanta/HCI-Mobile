package com.example.hci_mobile.components.more

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.R
import com.example.hci_mobile.api.data.model.User
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.navigation.AppDestinations
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.components.verticalBar.VerticalBar
import com.example.hci_mobile.ui.theme.AppTheme
import kotlinx.coroutines.delay
import java.util.Locale
import java.text.SimpleDateFormat
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    currentRoute: String? = null,
    onNavigateToRoute: (String) -> Unit = {},
    darkTheme: Boolean,
    onThemeUpdated: (Boolean) -> Unit,
    currentLocale: Locale,
    onLanguageChanged: (Locale) -> Unit,
    onNavigateBack: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState
    var showLanguageDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    // Detectar orientación
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    LaunchedEffect(Unit) {
        viewModel.getCurrentUser()
        delay(1000)
        isLoading = false
    }

    Scaffold(
        topBar = { TopBarWithBack(R.string.customize, onNavigateBack = onNavigateBack) },
        bottomBar = {
            if (isPortrait) {
                BottomBar(currentRoute = currentRoute, onNavigateToRoute = onNavigateToRoute)
            }
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Mostrar la barra vertical solo en modo horizontal
            if (!isPortrait) {
                VerticalBar(
                    currentRoute = currentRoute,
                    onNavigateToRoute = onNavigateToRoute,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(paddingValues) // Aplicar el padding calculado por el Scaffold
                        .width(80.dp) // Ancho fijo para la barra lateral
                )
            }

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(paddingValues)
                    .background(AppTheme.colorScheme.background)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .wrapContentHeight(),
                        shape = AppTheme.shape.container,
                        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.secondary),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (isLoading) {
                                LoadingIndicator()
                            } else {
                                // Información del usuario
                                AccountItem(
                                    label = stringResource(R.string.full_name),
                                    value = "${uiState.currentUser?.firstName} ${uiState.currentUser?.lastName}",
                                    style = AppTheme.typography.body
                                )
                                
                                Divider(color = AppTheme.colorScheme.onSecondary)
                                
                                AccountItem(
                                    label = stringResource(R.string.email),
                                    value = uiState.currentUser?.email ?: "",
                                    style = AppTheme.typography.body
                                )
                                
                                Divider(color = AppTheme.colorScheme.onSecondary)
                                
                                AccountItem(
                                    label = stringResource(R.string.birth_date_without_format),
                                    value = uiState.currentUser?.birthDate?.let {
                                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                                    } ?: "",
                                    style = AppTheme.typography.body
                                )

                                Divider(color = AppTheme.colorScheme.onSecondary)

                                // Dark Mode
                                AccountItem(
                                    label = stringResource(R.string.dark_mode),
                                    trailing = {
                                        Switch(
                                            checked = darkTheme,
                                            onCheckedChange = onThemeUpdated,
                                            colors = SwitchDefaults.colors(
                                                checkedThumbColor = AppTheme.colorScheme.primary,
                                                checkedTrackColor = AppTheme.colorScheme.primary.copy(alpha = 0.5f),
                                                uncheckedThumbColor = AppTheme.colorScheme.textColor,
                                                uncheckedTrackColor = AppTheme.colorScheme.textColor.copy(alpha = 0.5f)
                                            )
                                        )
                                    }
                                )

                                Divider(color = AppTheme.colorScheme.onSecondary)

                                // Language
                                AccountItem(
                                    label = stringResource(R.string.language),
                                    value = when (currentLocale.language) {
                                        "es" -> "Español"
                                        "en" -> "English"
                                        else -> "Español"
                                    },
                                    trailing = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Text(
                                                text = when (currentLocale.language) {
                                                    "es" -> "Español"
                                                    "en" -> "English"
                                                    else -> "Español"
                                                },
                                                style = AppTheme.typography.body,
                                                color = AppTheme.colorScheme.textColor
                                            )
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                                contentDescription = null,
                                                tint = AppTheme.colorScheme.textColor
                                            )
                                        }
                                    },
                                    onClick = { showLanguageDialog = true }
                                )

                                Divider(color = AppTheme.colorScheme.onSecondary)

                                // Logout
                                AccountItem(
                                    label = stringResource(R.string.logout),
                                    textColor = AppTheme.colorScheme.tertiary,
                                    onClick = {
                                        viewModel.logout()
                                        onNavigateToRoute(AppDestinations.LOGIN.route)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text(stringResource(R.string.select_language)) },
            text = {
                Column {
                    LanguageOption(
                        language = "Español",
                        isSelected = currentLocale.language == "es",
                        onClick = {
                            onLanguageChanged(Locale("es"))
                            showLanguageDialog = false
                        }
                    )
                    LanguageOption(
                        language = "English",
                        isSelected = currentLocale.language == "en",
                        onClick = {
                            onLanguageChanged(Locale("en"))
                            showLanguageDialog = false
                        }
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
private fun AccountItem(
    label: String,
    value: String = "",
    textColor: Color = AppTheme.colorScheme.textColor,
    style: TextStyle = AppTheme.typography.body,
    trailing: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = style,
            color = textColor
        )
        if (trailing != null) {
            trailing()
        } else if (value.isNotEmpty()) {
            Text(
                text = value,
                style = style,
                color = textColor
            )
        }
        if (onClick != null && trailing == null) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = textColor
            )
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = AppTheme.colorScheme.primary
        )
    }
}

@Composable
private fun LanguageOption(
    language: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(language)
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = AppTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    AppTheme(darkTheme = false) {
        SettingsScreen(
            darkTheme = false,
            onThemeUpdated = {},
            currentLocale = Locale.getDefault(),
            onLanguageChanged = {}
        )
    }
}
