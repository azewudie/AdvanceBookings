package com.aaron.advancebookings.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object CustomTheme {
    val colors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
    val spaces: CustomDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current
}