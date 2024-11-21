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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.res.stringResource
import java.util.Locale

// Primero creamos una data class para los campos editables
data class EditableField(
    val id: String,
    val label: String,
    var value: String,
    val icon: ImageVector = Icons.Default.Edit
)

@Composable
fun SettingsScreen(
    darkTheme: Boolean,
    onThemeUpdated: (Boolean) -> Unit,
    currentLocale: Locale,
    onLanguageChanged: (Locale) -> Unit
) {
    // Estado para los campos editables
    val fields = listOf(
        "name" to R.string.name,
        "lastName" to R.string.apellido,
        "email" to R.string.email,
        "telephone" to R.string.telephone
    )
    
    val editableFields = fields.map { (id, stringRes) ->
        EditableField(
            id = id,
            label = stringResource(stringRes),
            value = when(id) {
                "name" -> "Juan"
                "lastName" -> "Pérez"
                "email" -> "juan.perez@gmail.com"
                else -> "+54 11 1234-5678"
            }
        )
    }.toMutableList()
    
    // Mapa para mantener el estado de edición de cada campo
    val editingStates = remember {
        mutableStateMapOf<String, Boolean>().apply {
            fields.forEach { (id, _) ->
                this[id] = false
            }
        }
    }
    
    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Renderizar campos editables
        editableFields.forEach { field ->
            EditableItem(
                label = field.label,
                value = field.value,
                isEditing = editingStates[field.id] ?: false,
                onEditClick = { editingStates[field.id] = true },
                onValueChange = { newValue -> 
                    val index = editableFields.indexOfFirst { it.id == field.id }
                    if (index != -1) {
                        editableFields[index] = field.copy(value = newValue)
                    }
                },
                onDone = { editingStates[field.id] = false }
            )
            Divider(color = AppTheme.colorScheme.onSecondary, thickness = 1.dp)
        }

        // Modo oscuro
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
                onCheckedChange = { onThemeUpdated(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = AppTheme.colorScheme.primary,
                    uncheckedThumbColor = AppTheme.colorScheme.background
                )
            )
        }
        HorizontalDivider(thickness = 1.dp, color = AppTheme.colorScheme.onSecondary)

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
                text = stringResource(R.string.language),
                color = AppTheme.colorScheme.textColor,
                style = AppTheme.typography.body
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
    }

    // Diálogo de idioma (mantener el código existente)
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
    onDone: () -> Unit
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
            style = AppTheme.typography.body,
            modifier = Modifier.weight(1f)
        )
        
        if (isEditing) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier
                    .weight(2f)
                    .height(50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = AppTheme.colorScheme.primary,
                    focusedBorderColor = AppTheme.colorScheme.primary,
                    focusedContainerColor = AppTheme.colorScheme.onTertiary,
                    unfocusedContainerColor = AppTheme.colorScheme.onTertiary
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onDone() }),
                textStyle = AppTheme.typography.body
            )
        } else {
            Row(
                modifier = Modifier.weight(2f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    color = AppTheme.colorScheme.textColor,
                    style = AppTheme.typography.body,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
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

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        darkTheme = false,
        onThemeUpdated = {},
        currentLocale = Locale.getDefault(),
        onLanguageChanged = {}
    )
}
