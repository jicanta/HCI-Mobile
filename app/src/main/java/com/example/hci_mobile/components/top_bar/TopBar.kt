package com.example.hci_mobile.components.top_bar

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hci_mobile.R
import com.example.hci_mobile.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    TopAppBar(
        title = {
            IconButton( onClick = { /*TODO*/ },
                modifier = Modifier.size(85.dp))
            {
                Icon(
                    painter = painterResource(id = R.drawable.paygo),
                    contentDescription = "Paygo",
                    tint = AppTheme.colorScheme.textColor,
                    modifier = Modifier.fillMaxSize())
            }
            /*Text(
                text = stringResource(R.string.app_name)
            ,
            style = AppTheme.typography.title)*/
        },
        modifier = Modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = AppTheme.colorScheme.primary,
            titleContentColor = AppTheme.colorScheme.onPrimary)
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview(){
    AppTheme(darkTheme = false){
        TopBar()
    }
}