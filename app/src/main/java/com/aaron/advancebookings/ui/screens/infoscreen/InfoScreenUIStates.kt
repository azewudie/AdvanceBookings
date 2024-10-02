package com.aaron.advancebookings.ui.screens.infoscreen

import com.aaron.advancebookings.utilities.constants.AppConstants

data class InfoScreenUIStates(
    val infoHeader:String = AppConstants.EMPTY_STRING,
    val infoDetailList:List<String> = emptyList(),
    val infoFooter:List<String> = emptyList()
)