package com.example.hci_mobile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.ui.theme.AppTheme
import com.example.hci_mobile.components.navigation.NavigationApp
import java.util.Locale
import com.example.hci_mobile.api.data.model.Card
import com.example.hci_mobile.api.data.model.CardType
import com.example.hci_mobile.components.homeApi.canDeleteCard
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

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

@Composable
fun TestingApi(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState
    var token by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginEmail by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(70.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        if (uiState.isAuthenticated) {
            Button(onClick = { viewModel.logout() }) {
                Text(text = "Logout")
            }
           /* Button(onClick = { viewModel.getCurrentUser() }) {
                Text(text = "getCurrentUser")
            }
            Button(onClick = { viewModel.getCards() }) {
                Text(text = "getCards")
            }
            Button(onClick = { viewModel.addCard(
                Card(number= "444444444444444",
                    fullName = "pepe",
                    expirationDate = "12/25",
                    cvv = "123",
                    type = CardType.CREDIT) ) }) {
                Text(text = "addCard")
            }
            if (uiState.canDeleteCard) {
                Button(onClick = { viewModel.deleteCard(1) }) {
                    Text(text = "deleteCard")
                }
            }*/

            Text(
                text = "Balance actual: $${String.format("%.2f", uiState.balance?.balance)}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Button(onClick = { viewModel.getBalance() }) {
                Text(text = "Actualizar Balance")
            }

            Button(onClick = { viewModel.recharge(100.0) }) {
                Text(text = "Recargar $100")
            }
        } else {
            Text(
                text = "Registro",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = { Text("Fecha de nacimiento (YYYY-MM-DD)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = { 
                    viewModel.register(firstName, lastName, birthDate, email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "Registrarse")
            }

            Text(
                text = "Verificación",
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            
            TextField(
                value = token,
                onValueChange = { token = it },
                label = { Text("Token") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = { viewModel.verify(token) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "Verificar")
            }
            
            Text(
                text = "Login",
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            TextField(
                value = loginEmail,
                onValueChange = { loginEmail = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = loginPassword,
                onValueChange = { loginPassword = it },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = { viewModel.login(loginEmail, loginPassword) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "Login")
            }
        }
    }
}