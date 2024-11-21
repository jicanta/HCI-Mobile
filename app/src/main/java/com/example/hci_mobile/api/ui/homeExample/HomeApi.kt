package com.example.hci_mobile.api.ui.homeExample

import android.view.Display.Mode
import java.util.Locale

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.example.hci_mobile.R
import com.example.hci_mobile.api.data.model.YesNoAnswer
import com.example.hci_mobile.ui.theme.AppTheme

@Composable
fun HomeScreenApi(
    yesNoUiState: YesNoUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    when (yesNoUiState){
        is YesNoUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is YesNoUiState.Success -> ResultScreen(
            result = yesNoUiState.answer,
            modifier = modifier
        )
        is YesNoUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(R.drawable.visa),
        contentDescription = stringResource(R.string.loading),
        modifier = Modifier
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.paygo),
            contentDescription = stringResource(R.string.loading_failed),
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ResultScreen(
    result: YesNoAnswer,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        val imageLoader = LocalContext.current.imageLoader.newBuilder()
            .logger(DebugLogger())
            .build()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(result.imageUrl)
                .crossfade(true)
                .build(),
            imageLoader = imageLoader,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(300.dp).width(500.dp)
        )

        Text(
            text = result.answer.uppercase(Locale.getDefault()),
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.offset( y = 200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestingApiPreview(){
    AppTheme(){
        val viewModel: YesNoViewModel = viewModel()  //MUY IMPORTANTE QUE SE HAGA LA INSTANCIA DEL VIEWMODEL
        Surface(
            onClick = { viewModel.getAnswer() },
            modifier = Modifier.fillMaxSize()
        ){
            HomeScreenApi(viewModel.yesNoUiState, retryAction = {})
        }
    }
}