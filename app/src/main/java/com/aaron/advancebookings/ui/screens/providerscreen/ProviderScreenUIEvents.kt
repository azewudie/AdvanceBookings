package com.aaron.advancebookings.ui.screens.providerscreen

sealed  class ProviderScreenUIEvents {
    data object GetInitialData:ProviderScreenUIEvents()
     data object OnDateSelectedWrong: ProviderScreenUIEvents()
    data class OnWorkDayBookingSuccess(
        val workDay:String,
    ): ProviderScreenUIEvents()
    data class OnStartTimePicking(
        val startTime:String
    ):ProviderScreenUIEvents()
    data class OnEndTimePicking(
        val endTime:String
    ):ProviderScreenUIEvents()
    data object OnClickPickDay:ProviderScreenUIEvents()
    data object OnClickPickStartTime:ProviderScreenUIEvents()
    data object OnClickPickEndTime:ProviderScreenUIEvents()
}