package com.aaron.advancebookings.ui.screens.apphome

import com.aaron.advancebookings.utilities.constants.AppConstants

data class AppHomeScreenUIStates(
    val appHomeHeader: String = AppConstants.EMPTY_STRING,
    val appHomeDetailList: List<String> = emptyList(),
    val appHomeFooter: String = AppConstants.EMPTY_STRING,
    val appHomeClientButton: String = AppConstants.EMPTY_STRING,
    val appHomeProviderButton: String = AppConstants.EMPTY_STRING,
)