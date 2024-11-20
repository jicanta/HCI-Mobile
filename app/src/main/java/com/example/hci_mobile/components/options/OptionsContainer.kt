package com.example.hci_mobile.components.options

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OptionsContainer(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedValue by remember { mutableStateOf(value) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = AppTheme.shape.container,
        color = AppTheme.colorScheme.secondary,
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = label,
                style = AppTheme.typography.title
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditing) {
                    TextField(
                        value = editedValue,
                        onValueChange = { newValue ->
                            editedValue = newValue
                            onValueChange(newValue)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = AppTheme.colorScheme.secondary,
                            focusedContainerColor = AppTheme.colorScheme.secondary
                        ),
                        textStyle = AppTheme.typography.body
                    )
                } else {
                    Text(
                        text = value,
                        style = AppTheme.typography.body,
                        modifier = Modifier.weight(1f)
                    )
                }

                IconButton(
                    onClick = {
                        isEditing = !isEditing
                        if (!isEditing) {
                            onValueChange(editedValue)
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = AppTheme.colorScheme.primary,
                        contentColor = AppTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = if (isEditing) "Guardar $label" else "Editar $label"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOptionsContainer() {
    AppTheme(darkTheme = false){
        OptionsContainer(
            label = "Nombre",
            value = "Juan",
            onValueChange = {}
        )
    }
}