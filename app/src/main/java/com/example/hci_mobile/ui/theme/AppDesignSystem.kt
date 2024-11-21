package com.example.hci_mobile.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class AppColorScheme(
    val background: Color,
    val primary: Color,
    val secondary: Color,
    val onPrimary: Color,
    val textColor: Color,
    val tertiary: Color,
    val onSecondary: Color,
    val onTertiary: Color
)

data class AppTypography(
    val title: TextStyle,
    val body: TextStyle
)

data class AppShape(
    val container: Shape,
    val button: Shape
)

data class AppSize(
    val small: Dp,
    val medium: Dp,
    val large: Dp
)


val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        background = Color.Unspecified,
        primary = Color.Unspecified,
        secondary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        textColor = Color.Unspecified,
        tertiary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        onTertiary = Color.Unspecified
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        title = TextStyle.Default,
        body = TextStyle.Default
    )
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        container = RectangleShape,
        button = RectangleShape
    )
}

val LocalAppSize = staticCompositionLocalOf {
    AppSize(
        small = Dp.Unspecified,
        medium = Dp.Unspecified,
        large = Dp.Unspecified
    )
}