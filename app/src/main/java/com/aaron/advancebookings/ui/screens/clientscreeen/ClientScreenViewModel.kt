package com.aaron.advancebookings.ui.screens.clientscreeen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.aaron.advancebookings.R
import com.aaron.advancebookings.data.DataRepository
import com.aaron.advancebookings.data.remote.MainResponse
import com.aaron.advancebookings.data.remote.models.responses.ClientReservation
import com.aaron.advancebookings.data.remote.models.responses.ProviderSchedule
import com.aaron.advancebookings.di.appresources.AppResources
import com.aaron.advancebookings.framework.baseviewmodel.BaseViewModel
import com.aaron.advancebookings.ui.common.compose.MessageType
import com.aaron.advancebookings.ui.common.screenstate.UIState
import com.aaron.advancebookings.utilities.constants.AppConstants
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ClientScreenViewModel @Inject constructor(
    private val appResources: AppResources,
    private val dataRepository: DataRepository
) : BaseViewModel(dataRepository) {
    private val _screenState = MutableStateFlow(getUiInitialState())
    val screenState = _screenState.asStateFlow()
    private val _progressState = MutableStateFlow<UIState>(UIState.Success)
    val progressState = _progressState.asStateFlow()

    private fun getUiInitialState(): ClientScreenUIStates {
        return ClientScreenUIStates(
            confirmReservationButtonLabel = appResources.getString(R.string.confirm_button_label),
            availableTimeSlot = appResources.getString(R.string.available_time_slot),
            selectDayButtonLabel = appResources.getString(R.string.select_day)
        )
    }

    fun onClientScreenEvent(events: ClientScreenUIEvents) {
        when (events) {
            ClientScreenUIEvents.OnDateSelectedWrong -> {
                _screenState.update {
                    it.copy(
                        showCalendarDialog = mutableStateOf(false),
                        showToastMessage = mutableStateOf(true),
                        toastMessage = appResources.getString(R.string.booking_message_wrong),
                        toastMessageType = MessageType.MessageError,
                    )
                }
                updateToastMessage()
            }

            ClientScreenUIEvents.GetInitialData -> {
                _progressState.update { UIState.Loading }
                viewModelScope.launch {
                    val gson = Gson()
                    val clientData = dataStoreHelper.getClientReservationData()
                    val clientReservationInfo: ClientReservation =
                        gson.fromJson(clientData, ClientReservation::class.java)
                    if (dataStoreHelper.getClientReserveRealTime().isNotEmpty()) {
                        val parsedDateTime: LocalDateTime =
                            LocalDateTime.parse(dataStoreHelper.getClientReserveRealTime())
                        val bookingPlusThirtyMinutes = parsedDateTime.plusMinutes(30)
                        val currentDateTime = LocalDateTime.now()
                        if (
                            currentDateTime.isAfter(bookingPlusThirtyMinutes) &&
                            clientReservationInfo.isConfirmed.not()) {
                            clearClientReserveTime()
                        }
                    }
                    getInitialClientData()
                    getApiData()
                }

            }

            is ClientScreenUIEvents.OnClickAvailableTime -> {
                viewModelScope.launch {
                    val gson = Gson()
                    val clientData = dataStoreHelper.getClientReservationData()
                    val clientReservationInfo: ClientReservation =
                        gson.fromJson(clientData, ClientReservation::class.java)
                    _screenState.update {
                        it.copy(
                            userSchedule = events.selectedTime,
                            showUserSelectedTimeSlot = mutableStateOf(true),
                            showAvailableTimeSlot = mutableStateOf(false),
                            showSelectDayButton = mutableStateOf(false),
                            showNotAvailableTime = mutableStateOf(false),
                            showConfirmReservationButtonLabel = mutableStateOf(true)
                        )
                    }
                    clientReservationInfo.startTime = events.startTime
                    clientReservationInfo.endTime = events.endTime
                    dataStoreHelper.updateClientReservationData(gson.toJson(clientReservationInfo))
                    dataStoreHelper.updateClientReserveTime(events.selectedTime)
                    dataStoreHelper.updateIsClientReserveTimeSlot(true)
                    Log.d("TIME_CURRENT ", "onClientScreenEvent: ${events.currentDayTime}")
                    dataStoreHelper.updateClientReserveRealTime(events.currentDayTime)
                }
            }

            ClientScreenUIEvents.OnConfirmSelectedTime -> {
                viewModelScope.launch {
                    val gson = Gson()
                    val clientData = dataStoreHelper.getClientReservationData()
                    val clientReservationInfo: ClientReservation =
                        gson.fromJson(clientData, ClientReservation::class.java)
                    _screenState.update {
                        it.copy(
                            showToastMessage = mutableStateOf(true),
                            showConfirmReservationButtonLabel = mutableStateOf(false),
                            toastMessage = appResources.getString(R.string.confirm_message),
                            toastMessageType = MessageType.MessageSuccess
                        )
                    }
                    clientReservationInfo.isConfirmed = true
                    dataStoreHelper.updateClientReservationData(gson.toJson(clientReservationInfo))
                    updateToastMessage()
                }
            }

            ClientScreenUIEvents.OnClickSelectDay -> {
                _screenState.update {
                    it.copy(
                        showCalendarDialog = mutableStateOf(true),
                        toastMessage = appResources.getString(R.string.booking_message),
                        toastMessageType = MessageType.MessageSuccess,
                    )
                }
            }

            is ClientScreenUIEvents.OnBookingSuccess -> {
                Log.d("SelectedDay", "onClientScreenEvent: ${events.selectedDay}")
                Log.d(
                    "ProviderWorkDay",
                    "onClientScreenEvent:${screenState.value.providerAvailableDay}"
                )
                viewModelScope.launch {
                    val gson = Gson()
                    val clientData = dataStoreHelper.getClientReservationData()
                    val clientReservationInfo: ClientReservation =
                        gson.fromJson(clientData, ClientReservation::class.java)
                    clientReservationInfo.date = events.selectedDay
                    dataStoreHelper.updateClientReservationData(gson.toJson(clientReservationInfo))
                    _screenState.update {
                        it.copy(
                            showToastMessage = mutableStateOf(true),
                            showAvailableTimeSlot = mutableStateOf(events.selectedDay == screenState.value.providerAvailableDay),
                            showNotAvailableTime = mutableStateOf(events.selectedDay != screenState.value.providerAvailableDay)
                        )
                    }
                }
                updateToastMessage()
            }

            is ClientScreenUIEvents.ClearClientReserveTime -> {
                if (events.isReservationExpired) {
                    clearClientReserveTime()
                }
            }
        }
    }

    private fun getApiData() {
        viewModelScope.launch {
            when (val response = dataRepository.getApplicationApiService().getRandomUser()) {
                is MainResponse.Success -> {
                    val firstName = response.body.results[0].name?.first
                    val lastName = response.body.results[0].name?.last
                    _screenState.update {
                        response.body.results[0].picture?.let { it1 ->
                            response.body.results[0].id?.let { it2 ->
                                it.copy(
                                    clientImage = it1.medium,
                                    clientName = "$firstName $lastName",
                                    clientId = it2.value
                                )
                            }
                        }!!
                    }
                    _progressState.update { UIState.Success }
                }

                is MainResponse.ApiError -> {
                    Log.d("API_ERROR", "getApiData: Api Error")
                    _progressState.update { UIState.Success }
                }

                is MainResponse.NetworkError -> {
                    Log.d("Network_ERROR", "getApiData: Network Error")
                    _progressState.update { UIState.Success }
                }

                is MainResponse.UnknownError -> {
                    Log.d("UNKNOWN_ERROR", "getApiData: Unknown Error")
                    _progressState.update { UIState.Success }
                }
            }
        }
    }

    private fun updateToastMessage() {
        viewModelScope.launch {
            delay(2500)
            _screenState.update {
                it.copy(
                    showToastMessage = mutableStateOf(false)
                )
            }
        }
    }


    private fun getInitialClientData() {
        viewModelScope.launch {
            val gson = Gson()
            val providerData = dataStoreHelper.getProviderScheduleData()
            val clientData = dataStoreHelper.getClientReservationData()
            val providerSchedule: ProviderSchedule =
                gson.fromJson(providerData, ProviderSchedule::class.java)
            val clientReservationInfo: ClientReservation =
                gson.fromJson(clientData, ClientReservation::class.java)
            val isClientReserveTimeSlot = dataStoreHelper.getIsClientReserveTimeSlot()
            val checkDataNotEmpty =
                if (clientReservationInfo.date.isNotBlank() && providerSchedule.date.isNotBlank()) {
                    clientReservationInfo.date == providerSchedule.date
                } else {
                    false
                }
            Log.d("START_TIME", "getInitialClientData: ${providerSchedule.startTime.isNotBlank()}")
            Log.d("END_TIME", "getInitialClientData: ${providerSchedule.endTime.isNotBlank()}")
            val providerStartTime = if (providerSchedule.startTime.isNotBlank()) {
                splitHourMinutePeriod(providerSchedule.startTime)
            } else listOf("0","0","PM")

            val providerEndTime = if (providerSchedule.endTime.isNotBlank()) {
                splitHourMinutePeriod(providerSchedule.endTime)
            } else listOf("0","0","PM")

            _screenState.update {
                it.copy(
                    showConfirmReservationButtonLabel = mutableStateOf(
                        clientReservationInfo.isConfirmed.not()
                                && isClientReserveTimeSlot
                    ),
                    providerAvailableDay = providerSchedule.date,
                    showAvailableTimeSlot = mutableStateOf(
                        isClientReserveTimeSlot.not() &&
                                checkDataNotEmpty
                    ),
                    showUserSelectedTimeSlot = mutableStateOf(isClientReserveTimeSlot),
                    showSelectDayButton = mutableStateOf(
                        screenState.value.showAvailableTimeSlot.value.not()
                    ),
                    userSchedule = dataStoreHelper.getClientReserveTime(),
                    providerStartHours =providerStartTime.first().toInt(),
                    providerStartMinutes = providerStartTime[1].toInt(),
                    providerStartAM = providerStartTime.last().contains("AM"),
                    providerEndHours =providerEndTime.first().toInt(),
                    providerEndMinutes = providerEndTime[1].toInt(),
                    providerEndPM = providerEndTime.last().contains("PM"),


                    )
            }
        }
    }

    private fun clearClientReserveTime() {
        viewModelScope.launch {
            val gson = Gson()
            val clientData = dataStoreHelper.getClientReservationData()
            val clientReservationInfo: ClientReservation =
                gson.fromJson(clientData, ClientReservation::class.java)
            clientReservationInfo.date = AppConstants.EMPTY_STRING
            clientReservationInfo.startTime = AppConstants.EMPTY_STRING
            clientReservationInfo.endTime = AppConstants.EMPTY_STRING
            clientReservationInfo.isConfirmed = false
            dataStoreHelper.updateClientReserveTime(AppConstants.EMPTY_STRING)
            dataStoreHelper.updateIsClientReserveTimeSlot(false)
            dataStoreHelper.updateClientReserveRealTime(AppConstants.EMPTY_STRING)
            dataStoreHelper.updateClientReservationData(gson.toJson(clientReservationInfo))
            _screenState.update {
                it.copy(
                    showConfirmReservationButtonLabel = mutableStateOf(false),
                    showAvailableTimeSlot = mutableStateOf(false),
                    showUserSelectedTimeSlot = mutableStateOf(false),
                    showSelectDayButton = mutableStateOf(true),
                    userSchedule = AppConstants.EMPTY_STRING
                )
            }
        }
    }

    private fun splitHourMinutePeriod(value:String):List<String>{
        return value.split(":", " ")
    }
}