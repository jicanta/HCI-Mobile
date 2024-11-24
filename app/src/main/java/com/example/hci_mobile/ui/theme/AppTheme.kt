package com.example.hci_mobile.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val lightColorScheme = AppColorScheme(
    background = Grey,
    primary = DarkPurple,
    secondary = White,
    onPrimary = White,
    textColor = Black,
    tertiary = Pink,
    onSecondary = DarkGrey,
    onTertiary = LightPink
)

private val DarkNavy = Color(0xFF1A1B2E)      // Azul muy oscuro para el fondo
private val DeepBlue = Color(0xFF2D3250)      // Azul profundo para superficies
private val Orange = Color(0xFFFF8C42)        // Naranja vibrante para acentos
private val LightOrange = Color(0xFFFFB088)   // Naranja claro para elementos secundarios
private val DarkGrey2 = Color(0xFF2C2C2C)      // Gris oscuro para superficies secundarias
private val OffWhite = Color(0xFFF5F5F5)      // Blanco apagado para texto
private val BrightOrange = Color(0xFFFF9F5A)   // Naranja más brillante y vibrante

private val darkColorScheme = AppColorScheme(
    background = DarkNavy,        // Fondo principal de la app
    primary = DarkPurple,
    secondary = DeepBlue,         // Color para tarjetas y superficies elevadas
    onPrimary = OffWhite,        // Color del texto sobre elementos primary
    textColor = OffWhite,        // Color general del texto
    tertiary = BrightOrange,     // Color más brillante para "View all" y otros enlaces
    onSecondary = OffWhite,      // Color del texto sobre elementos secondary
    onTertiary = DarkGrey2
)

private val typography = AppTypography(
    title = TextStyle(
        fontFamily = roboto,  //TODO: aca deberia ir roboto
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    body = TextStyle(
        fontFamily = roboto,  //TODO: aca deberia ir roboto
    )
)

private val shape = AppShape(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50)
)

private val size = AppSize(
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
        content = {
            Surface(content = content)
        }
    )
}

object AppTheme {

    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val size: AppSize
        @Composable get() = LocalAppSize.current
}