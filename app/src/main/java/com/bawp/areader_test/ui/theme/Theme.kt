package com.bawp.areader_test.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController

private val DarkColorPalette =
    darkColors(primary = Purple200, primaryVariant = Purple700, secondary = Teal200)

private val LightColorPalette =
    lightColors(primary = Purple500, primaryVariant = Purple700, secondary = Teal200

        /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */)

@Composable
fun AReaderTestTheme(

    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
                    ) {
    //Set the SystemBarcolor to transparent: https://google.github.io/accompanist/systemuicontroller/
//    systemUiController?.setSystemBarsColor(
//        color = Color.Transparent,
//        darkIcons = true)

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(colors = colors, typography = Typography, shapes = Shapes, content = content)
}