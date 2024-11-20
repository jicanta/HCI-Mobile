package com.example.hci_mobile.components.movements
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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.hci_mobile.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovementsScreen()
        }
    }
}

@Preview
@Composable
fun MovementsScreenPreview() {
    MovementsScreen()
}

@Composable
fun MovementsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        MovementList()
    }
}

@Composable
fun TopBar(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5A2A82))
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Acción para ir atrás */ }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun MovementList() {
    val movements = listOf(
        Movement("Atuel café", "Pago de servicio", "-1.000,00", "Hoy"),
        Movement("Federico Magri", "Transferencia recibida", "+14.000,00", "10 de Septiembre"),
        Movement("Agostino Alfieri", "Transferencia enviada", "-4500,00", "10 de Septiembre"),
        Movement("Juan Ignacio Cant...", "Transferencia recibida", "+2.009,00", "9 de Septiembre"),
        Movement("Edwuin Cetre", "Transferencia recibida", "+6.000,00", "28 de Agosto"),
        Movement("Agostino Alfieri", "Transferencia recibida", "+20,50", "27 de Agosto")
    )
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        movements.groupBy { it.date }.forEach { (date, items) ->
            Text(
                text = date,
                style = AppTheme.typography.body,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp))
            items.forEach { movement ->
                MovementItem(movement)
            }
        }
    }
}

@Composable
fun MovementItem(movement: Movement) {
    Card(
        shape = AppTheme.shape.container,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null,
                tint = AppTheme.colorScheme.textColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movement.name,
                    fontWeight = FontWeight.Bold,
                    style = AppTheme.typography.body,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movement.description,
                    color = AppTheme.colorScheme.onSecondary,
                    style = AppTheme.typography.body,
                    fontSize = 12.sp
                )
            }
            Text(
                text = movement.amount,
                fontWeight = FontWeight.Bold,
                color = if (movement.amount.startsWith("+")) Color.Green else Color.Red,
                fontSize = 14.sp
            )
        }
    }
}

data class Movement(
    val name: String,
    val description: String,
    val amount: String,
    val date: String
)
