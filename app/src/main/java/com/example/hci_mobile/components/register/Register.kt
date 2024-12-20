package com.example.hci_mobile.components.register
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.navigation.AppDestinations
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun RegisterScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
    onNavigateToRoute: (String) -> Unit
) {
    val uiState = viewModel.uiState

    if(uiState.isAuthenticated){
        onNavigateToRoute(AppDestinations.HOME.route)
    }

    if(uiState.callSuccess){
        viewModel.clearCallSuccess()
        onNavigateToRoute(AppDestinations.VERIFY.route)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    var errorShown by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            RegisterCard(
                onRegister = { firstName, lastName, birthDate, email, password ->
                    viewModel.register(firstName, lastName, birthDate, email, password) {
                        onNavigateToRoute(
                            AppDestinations.VERIFY.route
                        )
                    }
                },
                onNavigateToRoute = onNavigateToRoute
            )
        }
    }
}

@Composable
fun RegisterCard(
    modifier: Modifier = Modifier,
    onRegister: (String, String, String, String, String) -> Unit,
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

    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var birthDate by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.welcomeRegister),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.body,
                modifier = Modifier.padding(bottom = 16.dp),
                color = AppTheme.colorScheme.textColor
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(
                    text = stringResource(R.string.name),
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.textColor
                ) },
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
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(
                    text = stringResource(R.string.lastName),
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.textColor
                ) },
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
                value = birthDate,
                onValueChange = { input ->
                    val formattedInput = when {
                        input.length == 4 && birthDate.length == 3 -> "$input-"
                        input.length == 7 && birthDate.length == 6 -> "$input-"
                        else -> input
                    }
                    
                    if (formattedInput.isEmpty() || formattedInput.matches(Regex("^\\d{0,4}(-\\d{0,2}(-\\d{0,2})?)?$"))) {
                        birthDate = formattedInput
                    }
                },
                label = { Text(
                    text = stringResource(R.string.birth_date),
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.textColor
                ) },
                placeholder = { Text("YYYY-MM-DD") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onRegister(name, lastName, birthDate, email, password)
                    //onNavigateToRoute(AppDestinations.VERIFY.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = AppTheme.shape.button,
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.register),
                    color = AppTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = AppTheme.typography.body
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onNavigateToRoute(AppDestinations.LOGIN.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = AppTheme.shape.button,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colorScheme.secondary,
                    contentColor = AppTheme.colorScheme.textColor
                )
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = AppTheme.typography.body
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    AppTheme(darkTheme = false) {
        RegisterScreen(onNavigateToRoute = {})
    }
}