package com.example.hci_mobile.components.register
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange

private fun formatBirthDate(text: String): String {
    val digitsOnly = text.filter { it.isDigit() }
    val groups = mutableListOf<String>()
    
    var currentIndex = 0
    while (currentIndex < digitsOnly.length && currentIndex < 8) {
        val endIndex = when {
            currentIndex == 0 -> minOf(4, digitsOnly.length)  // YYYY
            currentIndex == 4 -> minOf(6, digitsOnly.length)  // MM
            else -> digitsOnly.length  // DD
        }
        groups.add(digitsOnly.substring(currentIndex, endIndex))
        currentIndex = endIndex
    }
    
    return groups.joinToString("-")
}

@Composable
fun RegisterScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication)),
    onNavigateToRoute: (String) -> Unit
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    var errorShown by remember { mutableStateOf(false) }
    var birthDate by remember { mutableStateOf("") }
    var birthDateField by remember { mutableStateOf(TextFieldValue()) }

    // Observar el estado de la API
    LaunchedEffect(uiState.error) {
        if (uiState.error != null && !errorShown) {
            // Mostrar el mensaje de error en el Snackbar
            snackbarHostState.showSnackbar(
                message = when (uiState.error!!.code) {
                    400 -> "Datos inválidos"
                    409 -> "El email ya está registrado"
                    else -> uiState.error!!.message
                },
                duration = SnackbarDuration.Short
            )
            errorShown = true
            viewModel.clearError()
        } else if (uiState.error == null) {
            errorShown = false
        }
    }

    // Navegación separada del error
    LaunchedEffect(uiState.currentUser) {
        if (uiState.currentUser != null) {
            onNavigateToRoute(AppDestinations.VERIFY.route)
        }
    }

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
                    viewModel.register(firstName, lastName, birthDate, email, password)
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
    onNavigateToRoute: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthDateField by remember { mutableStateOf(TextFieldValue()) }

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
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(
                    text = stringResource(R.string.name),
                    style = AppTheme.typography.body
                ) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                    disabledContainerColor = AppTheme.colorScheme.onTertiary,
                    focusedTextColor = AppTheme.colorScheme.textColor,
                    unfocusedTextColor = AppTheme.colorScheme.textColor
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(
                    text = stringResource(R.string.lastName),
                    style = AppTheme.typography.body
                ) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                    disabledContainerColor = AppTheme.colorScheme.onTertiary,
                    focusedTextColor = AppTheme.colorScheme.textColor,
                    unfocusedTextColor = AppTheme.colorScheme.textColor
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(
                    text = stringResource(R.string.email),
                    style = AppTheme.typography.body
                ) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                    disabledContainerColor = AppTheme.colorScheme.onTertiary,
                    focusedTextColor = AppTheme.colorScheme.textColor,
                    unfocusedTextColor = AppTheme.colorScheme.textColor
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = birthDateField,
                onValueChange = { newValue ->
                    val cleanText = newValue.text.replace("-", "")
                    if (cleanText.all { it.isDigit() } && cleanText.length <= 8) {
                        birthDate = cleanText
                        val formatted = formatBirthDate(cleanText)
                        
                        // Calculate new cursor position
                        val cursorOffset = newValue.selection.start
                        val previousHyphens = birthDateField.text.take(cursorOffset).count { it == '-' }
                        val newHyphens = formatted.take(cursorOffset).count { it == '-' }
                        val cursorShift = newHyphens - previousHyphens
                        
                        birthDateField = TextFieldValue(
                            text = formatted,
                            selection = TextRange(newValue.selection.start + cursorShift)
                        )
                    }
                },
                label = { Text(
                    text = stringResource(R.string.birth_date),
                    style = AppTheme.typography.body
                ) },
                placeholder = { Text("YYYY-MM-DD") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                    disabledContainerColor = AppTheme.colorScheme.onTertiary,
                    focusedTextColor = AppTheme.colorScheme.textColor,
                    unfocusedTextColor = AppTheme.colorScheme.textColor
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(
                    text = stringResource(R.string.password),
                    style = AppTheme.typography.body
                ) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary,
                    disabledContainerColor = AppTheme.colorScheme.onTertiary,
                    focusedTextColor = AppTheme.colorScheme.textColor,
                    unfocusedTextColor = AppTheme.colorScheme.textColor
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onRegister(name, lastName, birthDate, email, password)
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
                    contentColor = AppTheme.colorScheme.primary
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
