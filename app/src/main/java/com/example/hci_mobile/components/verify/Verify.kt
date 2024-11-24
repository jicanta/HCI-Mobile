package com.example.hci_mobile.components.verify

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hci_mobile.MyApplication
import com.example.hci_mobile.components.homeApi.HomeViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hci_mobile.R

import com.example.hci_mobile.components.navigation.AppDestinations
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun VerifyScreen(
    onNavigateToRoute: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    val uiState = viewModel.uiState

   /* // Manejo de navegación en caso de éxito
    LaunchedEffect(uiState.callSuccess) {
        if (uiState.callSuccess) {
            //viewModel.clearCallSuccess()
            onNavigateToRoute(AppDestinations.LOGIN.route)
        }
    }*/

    if(uiState.callSuccess){
        viewModel.clearCallSuccess()
        onNavigateToRoute(AppDestinations.LOGIN.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        VerifyCard(
            onVerify = { token ->
                viewModel.verify(token){
                    onNavigateToRoute(
                        AppDestinations.LOGIN.route
                    )
                }
            },
            onNavigateToRoute = onNavigateToRoute
        )
    }
}

@Composable
fun VerifyCard(
    modifier: Modifier = Modifier,
    onNavigateToRoute: (String) -> Unit,
    onVerify: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
) {
    var token by remember { mutableStateOf("") }
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    // Manejo de errores
  /*  LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            error.message?.let {
                snackbarHostState.showSnackbar(
                    message = it,
                    duration = SnackbarDuration.Short
                )
            }
            viewModel.clearError()
        }
    }*/

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                    text = stringResource(R.string.verify_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.textColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = token,
                    onValueChange = { token = it },
                    label = { Text(
                        text = stringResource(R.string.token),
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

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onVerify(token)
                        // Removemos la navegación directa aquí
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = AppTheme.shape.button,
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(R.string.verify),
                        color = AppTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        style = AppTheme.typography.body
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) { data ->
            Snackbar(
                containerColor = AppTheme.colorScheme.primary,
                contentColor = AppTheme.colorScheme.onPrimary,
            ) {
                Text(
                    text = data.visuals.message,
                    style = AppTheme.typography.body
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyScreenPreview() {
    AppTheme(darkTheme = false) {
        VerifyScreen(onNavigateToRoute = {})
    }
}