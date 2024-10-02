package com.aaron.advancebookings.data.remote.models.responses

import com.aaron.advancebookings.utilities.constants.AppConstants

data class ProviderSchedule(
    val id: String = AppConstants.EMPTY_STRING,
    var date: String = AppConstants.EMPTY_STRING,
    var startTime: String = AppConstants.EMPTY_STRING,
    var endTime: String    = AppConstants.EMPTY_STRING
)