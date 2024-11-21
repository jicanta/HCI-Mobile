package com.example.hci_mobile.components.more
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hci_mobile.R
import com.example.hci_mobile.ui.theme.White
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.text.input.ImeAction
import com.example.hci_mobile.ui.theme.AppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.isSystemInDarkTheme
import java.util.Locale


@Composable
fun SettingsScreen(
    darkTheme: Boolean,
    onThemeUpdated: (Boolean) -> Unit,
    currentLocale: Locale,
    onLanguageChanged: (Locale) -> Unit
) {
    var name by remember { mutableStateOf("Juan") }
    var lastName by remember { mutableStateOf("Pérez") }
    var email by remember { mutableStateOf("juan.perez@gmail.com") }
    var phone by remember { mutableStateOf("+54 11 1234-5678") }
    
    var editingName by remember { mutableStateOf(false) }
    var editingLastName by remember { mutableStateOf(false) }
    var editingEmail by remember { mutableStateOf(false) }
    var editingPhone by remember { mutableStateOf(false) }
    
    var showLanguageDialog by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Español") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        
        // Campo Nombre
        EditableItem(
            label = "Nombre",
            value = name,
            isEditing = editingName,
            onEditClick = { editingName = true },
            onValueChange = { name = it },
            onDone = { editingName = false },
            icon = Icons.Default.Edit
        )
        Divider(color = Color.LightGray, thickness = 1.dp)
        
        // Campo Apellido
        EditableItem(
            label = "Apellido",
            value = lastName,
            isEditing = editingLastName,
            onEditClick = { editingLastName = true },
            onValueChange = { lastName = it },
            onDone = { editingLastName = false },
            icon = Icons.Default.Edit
        )
        Divider(color = Color.LightGray, thickness = 1.dp)
        
        // Campo Email
        EditableItem(
            label = "Email",
            value = email,
            isEditing = editingEmail,
            onEditClick = { editingEmail = true },
            onValueChange = { email = it },
            onDone = { editingEmail = false },
            icon = Icons.Default.Edit
        )
        Divider(color = Color.LightGray, thickness = 1.dp)
        
        // Campo Teléfono
        EditableItem(
            label = "Teléfono",
            value = phone,
            isEditing = editingPhone,
            onEditClick = { editingPhone = true },
            onValueChange = { phone = it },
            onDone = { editingPhone = false },
            icon = Icons.Default.Edit
        )
        Divider(color = Color.LightGray, thickness = 1.dp)

        // Modo oscuro
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Modo oscuro",
                color = AppTheme.colorScheme.textColor,
                fontSize = 16.sp
            )
            Switch(
                checked = darkTheme,
                onCheckedChange = { onThemeUpdated(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = AppTheme.colorScheme.primary,
                    uncheckedThumbColor = Color.LightGray
                )
            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)

        // Selector de idioma
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showLanguageDialog = true }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Idioma",
                color = AppTheme.colorScheme.textColor,
                fontSize = 16.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (currentLocale.language) {
                        "es" -> "Español"
                        "en" -> "English"
                        else -> "Español"
                    },
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = AppTheme.colorScheme.primary
                )
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }

    // Diálogo de idioma (mantener el código existente)
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text("Seleccionar idioma") },
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
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun EditableItem(
    label: String,
    value: String,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = AppTheme.colorScheme.textColor,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        
        if (isEditing) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier.weight(2f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onDone() })
            )
        } else {
            Row(
                modifier = Modifier.weight(2f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Editar $label",
                        tint = Color(0xFF5A2A82)
                    )
                }
            }
        }
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

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        darkTheme = false,
        onThemeUpdated = {},
        currentLocale = Locale.getDefault(),
        onLanguageChanged = {}
    )
}
