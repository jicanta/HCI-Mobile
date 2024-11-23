package com.example.hci_mobile.components.more

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.R
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.navigation.AppDestinations
import com.example.hci_mobile.components.top_bar.TopBarWithBack
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
    
    LaunchedEffect(Unit) {
        viewModel.getCurrentUser()
        delay(1000)
        isLoading = false
    }

    Scaffold(
        topBar = { TopBarWithBack(R.string.customize, onNavigateBack = onNavigateBack) },
        bottomBar = { BottomBar(currentRoute = currentRoute, onNavigateToRoute = onNavigateToRoute) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.dark_mode),
                    color = AppTheme.colorScheme.textColor,
                    style = AppTheme.typography.body
                )
                Switch(
                    checked = darkTheme,
                    onCheckedChange = onThemeUpdated,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppTheme.colorScheme.primary,
                        uncheckedThumbColor = AppTheme.colorScheme.background
                    )
                )
            }
            Divider(color = AppTheme.colorScheme.onSecondary, thickness = 1.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showLanguageDialog = true }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.language),
                    color = AppTheme.colorScheme.textColor,
                    style = AppTheme.typography.body
                )
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
                        color = AppTheme.colorScheme.textColor,
                        style = AppTheme.typography.body
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = AppTheme.colorScheme.primary
                    )
                }
            }
            Divider(color = AppTheme.colorScheme.onSecondary, thickness = 1.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { 
                        viewModel.logout()
                        onNavigateToRoute(AppDestinations.LOGIN.route)
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.logout),
                    color = AppTheme.colorScheme.tertiary,
                    style = AppTheme.typography.body
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = AppTheme.colorScheme.tertiary
                )
            }
            Divider(color = AppTheme.colorScheme.onSecondary, thickness = 1.dp)

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    color = AppTheme.colorScheme.primary
                )
            } else if (uiState.currentUser == null) {
                Text(
                    text = "No se pudieron cargar los datos del usuario",
                    modifier = Modifier.padding(16.dp),
                    color = AppTheme.colorScheme.tertiary
                )
            } else {
                uiState.currentUser?.let { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = AppTheme.colorScheme.secondary
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            UserInfoItem(
                                label = stringResource(R.string.full_name),
                                value = "${user.firstName} ${user.lastName}"
                            )
                            Divider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = AppTheme.colorScheme.onSecondary
                            )
                            UserInfoItem(
                                label = stringResource(R.string.email),
                                value = user.email
                            )
                            Divider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = AppTheme.colorScheme.onSecondary
                            )
                            UserInfoItem(
                                label = stringResource(R.string.birth_date),
                                value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(user.birthDate)
                            )
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
private fun UserInfoItem(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = AppTheme.typography.body,
            color = AppTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = AppTheme.typography.body,
            color = AppTheme.colorScheme.secondary,
            modifier = Modifier.weight(2f)
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
