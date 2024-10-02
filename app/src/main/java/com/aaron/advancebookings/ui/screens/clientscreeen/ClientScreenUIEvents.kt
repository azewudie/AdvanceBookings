package com.aaron.advancebookings.ui.screens.clientscreeen

sealed class ClientScreenUIEvents {
    data object GetInitialData: ClientScreenUIEvents()
    data class ClearClientReserveTime(
        val isReservationExpired:Boolean
    ): ClientScreenUIEvents()
    data object OnDateSelectedWrong:ClientScreenUIEvents()
    data class OnBookingSuccess(
       val  selectedDay:String,
    ):ClientScreenUIEvents()
    data class OnClickAvailableTime(
        val selectedTime:String,
        val startTime:String,
        val endTime:String,
        val currentDayTime:String
    ):ClientScreenUIEvents()
    data object OnConfirmSelectedTime:ClientScreenUIEvents()
    data object OnClickSelectDay:ClientScreenUIEvents()
}