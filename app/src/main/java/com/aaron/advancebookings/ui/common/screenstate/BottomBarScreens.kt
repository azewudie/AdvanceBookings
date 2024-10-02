package com.aaron.advancebookings.ui.common.screenstate

import com.aaron.advancebookings.navigation.AppScreens

sealed class BottomBarScreens(
    val route:String,
    val sectionName:String,
    val enable:Boolean = false,
) {
    data object Home:BottomBarScreens(
        route = AppScreens.AppHomeScreen.route,
        sectionName = "Home",
        enable = true
    )
    data object Provider:BottomBarScreens(
        route = AppScreens.Provider.route,
        sectionName = "Provider",
        enable = true
    )
    data object Client:BottomBarScreens(
        route = AppScreens.Client.route,
        sectionName = "Client",
        enable = true
    )
    data object Info:BottomBarScreens(
        route = AppScreens.Info.route,
        sectionName = "Info",
        enable = true
    )
}