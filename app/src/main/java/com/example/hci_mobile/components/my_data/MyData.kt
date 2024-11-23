package com.example.hci_mobile.components.my_data
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.R
import com.example.hci_mobile.api.data.model.Wallet
import com.example.hci_mobile.components.bottom_bar.BottomBar
import com.example.hci_mobile.components.homeApi.HomeViewModel
import com.example.hci_mobile.components.top_bar.TopBar
import com.example.hci_mobile.components.top_bar.TopBarWithBack
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun AccountDataScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState
    viewModel.getWalletDetails()

    Scaffold(
        topBar = { TopBarWithBack(R.string.data, onNavigateBack = onNavigateBack) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            AccountCard(
                wallet = uiState.wallet,
                onModifyAlias = { /* TODO: Implementar modificación de alias */ }
            )
        }
    }
}

@Composable
fun AccountCard(
    wallet: Wallet?,
    onModifyAlias: () -> Unit
) {
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Balance
            AccountItem(
                label = stringResource(R.string.account_balance),
                value = wallet?.balance?.toString() ?: "0.0",
                style = AppTheme.typography.body
            )
            
            // CBU
            AccountItem(
                label = stringResource(R.string.cbu),
                value = wallet?.cbu ?: "",
                copyOption = true,
                style = AppTheme.typography.body
            )
            
            // Alias
            AccountItem(
                label = stringResource(R.string.alias),
                value = wallet?.alias ?: "",
                copyOption = true,
                style = AppTheme.typography.body
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Modificar Alias
            Button(
                onClick = onModifyAlias,
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primary),
                shape = AppTheme.shape.button,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = stringResource(R.string.change_alias),
                    color = AppTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = AppTheme.typography.body
                )
            }
        }
    }
}

@Composable
fun AccountItem(
    label: String,
    value: String,
    copyOption: Boolean = false,
    style: TextStyle = AppTheme.typography.body
) {
    val context = LocalContext.current
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = label,
                style = style,
                color = AppTheme.colorScheme.textColor.copy(alpha = 0.6f)
            )
            Text(
                text = value,
                style = style,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colorScheme.textColor
            )
        }
        if (copyOption) {
            TextButton(
                onClick = { copyToClipboard(context, value, label) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = AppTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.copy),
                    style = style,
                    color = AppTheme.colorScheme.tertiary
                )
            }
        }
    }
}

private fun copyToClipboard(context: Context, text: String, label: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    
    // Mostrar mensaje de confirmación
    Toast.makeText(
        context,
        context.getString(R.string.copied_to_clipboard).replace("{label}", label),
        Toast.LENGTH_SHORT
    ).show()
}

@Preview(showBackground = true)
@Composable
fun AccountDataScreenPreview() {
    AppTheme(darkTheme = false) {
        AccountDataScreen()
    }
}
