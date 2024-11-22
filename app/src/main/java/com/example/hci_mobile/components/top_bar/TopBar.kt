package com.example.hci_mobile.components.top_bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.R
import com.example.hci_mobile.ui.theme.AppTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    TopAppBar(
        title = {
            IconButton( onClick = { },
                modifier = Modifier.size(120.dp))
            {
                Icon(
                    painter = painterResource(id = R.drawable.paygo),
                    contentDescription = "Paygo",
                    tint = Color.Unspecified,
                    modifier = Modifier.fillMaxSize())
            }
        },
        modifier = Modifier.height(110.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colorScheme.primary,
            titleContentColor = AppTheme.colorScheme.onPrimary)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithBack(
    @StringRes title: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 48.dp)
            ) {
                Text(
                    text = stringResource(id = title),
                    style = AppTheme.typography.title,
                    color = AppTheme.colorScheme.onPrimary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = AppTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = Modifier.height(110.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colorScheme.primary,
            titleContentColor = AppTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview(){
    AppTheme(darkTheme = false){
        TopBar()
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarWithBackPreview(){
    AppTheme(darkTheme = false){
        TopBarWithBack(
            title = R.string.app_name,
            onNavigateBack = {}
        )
    }
}