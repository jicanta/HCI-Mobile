package com.example.hci_mobile.components.login
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hci_mobile.R
import com.example.hci_mobile.components.register.RegisterCard
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun LoginScreen() {
    Scaffold(
        topBar = { TopBar() }, // Reutiliza tu TopBar
        containerColor = Color(0xFFF5F5F5) // Fondo gris claro
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LoginCard()
        }
    }
}

@Composable
fun LoginCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight(),
        shape = AppTheme.shape.container,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título principal
            Text(
                text = stringResource(R.string.welcomeLogin),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo de texto: Usuario o teléfono
            var username by remember { mutableStateOf("") }
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.usserOrPhoneNumber)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto: Contraseña
            var password by remember { mutableStateOf("") }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Enlace para recuperar contraseña
            TextButton(
                onClick = { /* Acción para recuperar contraseña */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(R.string.forgotPassword),
                    color = AppTheme.colorScheme.primary, // Color púrpura
                    fontSize = 14.sp,
                    style = AppTheme.typography.body
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de iniciar sesión
            Button(
                onClick = { /* Acción para iniciar sesión */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = AppTheme.shape.button,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5E2A84))
            ) {
                Text(
                    text = stringResource(R.string.continuar),
                    color = AppTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = AppTheme.typography.body
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { /* Acción para registrarse */ }) {
                Text(
                    text = stringResource(R.string.register),
                    color = AppTheme.colorScheme.primary, // Púrpura oscuro
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
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
