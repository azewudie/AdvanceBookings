package com.aaron.advancebookings.navigation


sealed class AppScreens(
    val route: String
) {
    data object SplashScreen : AppScreens(AppRouteNames.SPLASH_SCREEN)
    data object AppHomeScreen : AppScreens(AppRouteNames.APP_HOME_SCREEN)
    data object Provider : AppScreens(AppRouteNames.PROVIDER_SCREEN)
    data object Client : AppScreens(AppRouteNames.CLIENT_SCREEN)
    data object Info : AppScreens(AppRouteNames.INFO_SCREEN)
}