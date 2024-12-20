package com.example.hci_mobile.components.more

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.navigation.AppDestinations
import com.example.hci_mobile.components.navigation.ResponsiveNavigation
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

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        viewModel.getCurrentUser()
        delay(1000)
        isLoading = false
    }

    Scaffold(
        containerColor = AppTheme.colorScheme.background,
        topBar = { TopBarWithBack(R.string.configuration, onNavigateBack = onNavigateBack) },
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

            Box(
                modifier = modifier
                    .weight(1f)
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .fillMaxWidth(0.9f),
                    shape = AppTheme.shape.container,
                    colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.secondary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState()), // Agregamos verticalScroll
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (isLoading) {
                            LoadingIndicator()
                        } else {
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

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.select_language),
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.textColor
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LanguageOption(
                        language = "Español",
                        isSelected = currentLocale.language == "es",
                        onClick = {
                            onLanguageChanged(Locale("es"))
                            showLanguageDialog = false
                        }
                    )
                    Divider(color = AppTheme.colorScheme.onSecondary)
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
                TextButton(
                    onClick = { showLanguageDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = AppTheme.colorScheme.textColor
                    )
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = AppTheme.typography.body
                    )
                }
            },
            containerColor = AppTheme.colorScheme.secondary,
            shape = AppTheme.shape.container
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
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = language,
            style = AppTheme.typography.body,
            color = AppTheme.colorScheme.textColor
        )
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
