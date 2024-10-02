package com.aaron.advancebookings.data.remote.models.responses

import com.aaron.advancebookings.utilities.constants.AppConstants
import com.google.gson.Gson

data class ClientReservation(
    val id: String = AppConstants.EMPTY_STRING,
    var date: String = AppConstants.EMPTY_STRING,
    var startTime: String = AppConstants.EMPTY_STRING,
    var endTime: String = AppConstants.EMPTY_STRING,
    var isConfirmed: Boolean = false,
    val expirationTime: String = AppConstants.EMPTY_STRING,
)
object DefaultJsonValue{
    private val gson = Gson()
    val defaultClientReservationJsonValue: String = gson.toJson(ClientReservation())
    val defaultProviderScheduleJsonValue: String = gson.toJson(ProviderSchedule())
}