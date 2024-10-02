package com.aaron.advancebookings.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

data class CustomTypography(
    val title0Regular: TextStyle,
    val title1Regular: TextStyle,
    val title2Regular: TextStyle,
    val largeTitleRegular:TextStyle,
    val textFieldError: TextStyle,
    val titleBold: TextStyle,
    val bodyLink:TextStyle
)

val LocalTypography = staticCompositionLocalOf {
    CustomTypography(
        title0Regular = TextStyle.Default,
        title1Regular = TextStyle.Default,
        title2Regular = TextStyle.Default,
        largeTitleRegular = TextStyle.Default,
        textFieldError = TextStyle.Default,
        titleBold = TextStyle.Default,
        bodyLink = TextStyle.Default
    )
}