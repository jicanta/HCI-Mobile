package com.example.hci_mobile.components.login
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.R
import com.example.hci_mobile.api.data.model.ApiError
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.navigation.AppDestinations
import com.example.hci_mobile.ui.theme.AppTheme
@Composable
fun LoginScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
    onNavigateToRoute: (String) -> Unit = {}
) {
    val uiState = viewModel.uiState

   /* if(uiState.isAuthenticated){
        onNavigateToRoute(AppDestinations.HOME.route)
    }*/

    if(uiState.callSuccess){
        viewModel.clearCallSuccess()
        onNavigateToRoute(AppDestinations.HOME.route)
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        val isTablet = maxWidth > 600.dp
        LoginCard(
            modifier = if (isTablet) {
                Modifier
                    .fillMaxWidth(0.5f)
                    .wrapContentHeight()
            } else {
                Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
            },
            onLogin = { email, password ->
                viewModel.login(email, password)
            },
            onNavigateToRoute = onNavigateToRoute
        )
    }
}

@Composable
fun LoginCard(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            error.message?.let {
                snackbarHostState.showSnackbar(
                    message = it,
                    duration = SnackbarDuration.Short
                )
            }
            viewModel.clearError()
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = modifier,
            shape = AppTheme.shape.container,
            colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.secondary),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.welcome_login),
                    fontSize = if (modifier.fillMaxWidth(0.5f) != Modifier) 20.sp else 16.sp, // Tamaño más grande en tablets
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.textColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(
                        text = stringResource(R.string.email),
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.textColor
                    ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AppTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                        disabledContainerColor = AppTheme.colorScheme.onTertiary,
                        focusedTextColor = AppTheme.colorScheme.textColor,
                        unfocusedTextColor = AppTheme.colorScheme.textColor,
                        focusedBorderColor = AppTheme.colorScheme.tertiary,
                        unfocusedBorderColor = AppTheme.colorScheme.tertiary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(
                        text = stringResource(R.string.password),
                        style = AppTheme.typography.body,
                        color = AppTheme.colorScheme.textColor
                    ) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AppTheme.colorScheme.onTertiary,
                        unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                        disabledContainerColor = AppTheme.colorScheme.onTertiary,
                        focusedTextColor = AppTheme.colorScheme.textColor,
                        unfocusedTextColor = AppTheme.colorScheme.textColor,
                        focusedBorderColor = AppTheme.colorScheme.tertiary,
                        unfocusedBorderColor = AppTheme.colorScheme.tertiary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { /* Acción para recuperar contraseña */ },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = stringResource(R.string.forgotPassword),
                        color = AppTheme.colorScheme.textColor,
                        fontSize = 14.sp,
                        style = AppTheme.typography.body
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onLogin(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = AppTheme.shape.button,
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        color = AppTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        style = AppTheme.typography.body
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = { onNavigateToRoute(AppDestinations.REGISTER.route) }
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        color = AppTheme.colorScheme.textColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        style = AppTheme.typography.body
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) { data ->
            Snackbar(
                containerColor = AppTheme.colorScheme.primary,
                contentColor = AppTheme.colorScheme.onPrimary,
            ) {
                Text(
                    text = data.visuals.message,
                    style = AppTheme.typography.body
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppTheme(darkTheme = false) {
        LoginScreen()
    }
}
