package com.aaron.advancebookings.ui.screens.apphome

import androidx.navigation.NavController


sealed class AppHomeScreenUIEvents{
    data class OnClickGetStartClient(
        val navController: NavController
    ):AppHomeScreenUIEvents()
    data class OnClickGetStartProvider(
        val navController: NavController
    ):AppHomeScreenUIEvents()
}