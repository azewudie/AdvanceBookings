package com.aaron.advancebookings.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColors(
    val text0: Color,
    val text1: Color,
    val text2: Color,
    val textWhite: Color,
    val backGrounds: Color,
    val cardDefault: Color,
    val action1: Color,
    val statusError1: Color,
    val logoColor:Color
)

val LocalColors = staticCompositionLocalOf {
    CustomColors(
        text0 = Color.Unspecified,
        text1 = Color.Unspecified,
        text2 = Color.Unspecified,
        textWhite = Color.Unspecified,
        backGrounds = Color.Unspecified,
        cardDefault = Color.Unspecified,
        action1 = Color.Unspecified,
        statusError1 = Color.Unspecified,
        logoColor = Color.Unspecified
    )
}