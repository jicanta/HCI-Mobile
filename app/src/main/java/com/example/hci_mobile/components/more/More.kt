package com.example.hci_mobile.components.more
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hci_mobile.R
import com.example.hci_mobile.ui.theme.White


@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDEDED)) // Fondo gris claro
    ) {
        // Barra superior
        TopBar(title = "Más")
        HorizontalDivider(color = White)
        // Sección de perfil
        UserProfileSection(
            username = "Jpincha2009",
            onVisitWebsiteClick = { /* Acción al visitar página web */ }
        )

        // Lista de opciones
        SettingsOptions()
    }
}
@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}


@Composable
fun TopBar(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5A2A82)) // Color púrpura
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Acción para regresar */ }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun UserProfileSection(username: String, onVisitWebsiteClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5A2A82))
            //.background(R.color.purple_700) // corregir, no se pq no funca
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Icono de usuario",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = username,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onVisitWebsiteClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Visite nuestra página web",
                    color = Color(R.color.purple_700),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Abrir página web",
                    tint = Color(0xFF5A2A82)
                )
            }
        }
    }
}

@Composable
fun SettingsOptions() {
    Column(modifier = Modifier.fillMaxWidth()) {
        val options = listOf(
            SettingOption("Recibir notificaciones", isToggle = true),
            SettingOption("Modo oscuro", isToggle = true),
            SettingOption("Recuperar contraseña", isToggle = false),
            SettingOption("Cerrar sesión", isToggle = false)
        )

        options.forEachIndexed { index, option ->
            SettingItem(option)
            if (index < options.size - 1) HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFF5A2A82)
            )
        }
    }
}

@Composable
fun SettingItem(option: SettingOption) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = option.title,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        if (option.isToggle) {
            Switch(
                checked = true,
                onCheckedChange = { /* Acción al cambiar el switch */ },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF5A2A82),
                    uncheckedThumbColor = Color.LightGray
                )
            )
        } else {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Abrir opción",
                tint = Color(0xFF5A2A82)
            )
        }
    }
}

data class SettingOption(val title: String, val isToggle: Boolean)
