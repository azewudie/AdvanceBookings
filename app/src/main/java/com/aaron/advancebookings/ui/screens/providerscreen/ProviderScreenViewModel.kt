package com.aaron.advancebookings.ui.screens.providerscreen

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
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderScreenViewModel @Inject constructor(
    private val appResources: AppResources,
    private val dataRepository: DataRepository
) : BaseViewModel(dataRepository) {
    private val _screenState = MutableStateFlow(getUiInitialState())
    val screenState = _screenState.asStateFlow()
    private val _progressState = MutableStateFlow<UIState>(UIState.Success)
    val progressState = _progressState.asStateFlow()

    private fun getUiInitialState(): ProviderScreenUIStates {
        return ProviderScreenUIStates(
            pickDay = appResources.getString(R.string.provide_pick_day),
            pickStartTime = appResources.getString(R.string.provide_pick_time_start),
            pickEndTime = appResources.getString(R.string.provide_pick_time_end)
        )
    }

    fun onProviderScreenEvent(events: ProviderScreenUIEvents) {
        when (events) {
            ProviderScreenUIEvents.OnDateSelectedWrong -> {
                _screenState.update {
                    it.copy(
                        showCalendarDialog = mutableStateOf(false)
                    )
                }
            }

            ProviderScreenUIEvents.GetInitialData -> {
                _progressState.update { UIState.Loading }
                viewModelScope.launch {
                    val clientData = dataStoreHelper.getClientReservationData()
                    val gson = Gson()
                    val clientReservation: ClientReservation =
                        gson.fromJson(clientData, ClientReservation::class.java)
                    _screenState.update {
                        it.copy(
                            isSomeOneBookTime = mutableStateOf(clientReservation.isConfirmed),
                            upcomingClientSchedule = "${clientReservation.date}:  ${clientReservation.startTime} - ${clientReservation.endTime}"
                        )
                    }
                }
                getInitialData()
                getApiData()
            }

            ProviderScreenUIEvents.OnClickPickDay -> {
                _screenState.update {
                    it.copy(
                        showCalendarDialog = mutableStateOf(true)
                    )
                }
            }

            ProviderScreenUIEvents.OnClickPickEndTime -> {
                _screenState.update {
                    it.copy(
                        showEndTimePickerDialog = mutableStateOf(true)
                    )
                }
            }

            ProviderScreenUIEvents.OnClickPickStartTime -> {
                _screenState.update {
                    it.copy(
                        showTimePickerDialog = mutableStateOf(true)
                    )
                }
            }

            is ProviderScreenUIEvents.OnWorkDayBookingSuccess -> {
                viewModelScope.launch {
                    val gson = Gson()
                    val providerData = dataStoreHelper.getProviderScheduleData()
                    val providerSchedule: ProviderSchedule =
                        gson.fromJson(providerData, ProviderSchedule::class.java)
                    _screenState.update {
                        it.copy(
                            showCalendarDialog = mutableStateOf(false),
                            showToastMessage = mutableStateOf(true),
                            toastMessage = "Booking confirmed for your selected day.",
                            toastMessageType = MessageType.MessageSuccess,
                            showWorkDay = mutableStateOf(true),
                            monthDayYear = appResources.getStringList(R.array.Provider_selected_work_day_time)[0] + " ${events.workDay}"
                        )
                    }
                    providerSchedule.date = events.workDay
                    Log.d("workday", "onProviderScreenEvent: ${events.workDay}")
                    dataStoreHelper.updateProviderScheduleData(gson.toJson(providerSchedule))
                    dataStoreHelper.updateUserData(screenState.value.monthDayYear)
                    dataStoreHelper.updateShowMonthDayYear(true)
                    updateToastMessage()
                }
            }

            is ProviderScreenUIEvents.OnEndTimePicking -> {
                viewModelScope.launch {
                    val gson = Gson()
                    val providerData = dataStoreHelper.getProviderScheduleData()
                    val providerSchedule: ProviderSchedule =
                        gson.fromJson(providerData, ProviderSchedule::class.java)
                    _screenState.update {
                        it.copy(
                            showEndTimePickerDialog = mutableStateOf(false),
                            showEndTime = mutableStateOf(true),
                            showToastMessage = mutableStateOf(true),
                            toastMessage = "Booking confirmed for the selected start time.",
                            toastMessageType = MessageType.MessageSuccess,
                            endTimeHoursMinutes = appResources.getStringList(R.array.Provider_selected_work_day_time)
                                .last() + " ${events.endTime}"
                        )
                    }
                    providerSchedule.endTime = events.endTime
                    Log.d("workEndTime", "onProviderScreenEvent: ${events.endTime}")
                    dataStoreHelper.updateProviderEndTime(screenState.value.endTimeHoursMinutes)
                    dataStoreHelper.updateProviderScheduleData(gson.toJson(providerSchedule))
                    dataStoreHelper.updateShowProviderEndTime(true)
                    updateToastMessage()
                }

            }

            is ProviderScreenUIEvents.OnStartTimePicking -> {
                viewModelScope.launch {
                    val gson = Gson()
                    val providerData = dataStoreHelper.getProviderScheduleData()
                    val providerSchedule: ProviderSchedule =
                        gson.fromJson(providerData, ProviderSchedule::class.java)
                    _screenState.update {
                        it.copy(
                            showTimePickerDialog = mutableStateOf(false),
                            showStartTime = mutableStateOf(true),
                            showToastMessage = mutableStateOf(true),
                            toastMessage = "Booking confirmed for the selected start time.",
                            toastMessageType = MessageType.MessageSuccess,
                            startTimeHoursMinutes = appResources.getStringList(R.array.Provider_selected_work_day_time)[1] + " ${events.startTime}"
                        )
                    }
                    providerSchedule.startTime = events.startTime

                    Log.d("workEndTime", "onProviderScreenEvent: ${events.startTime}")
                    dataStoreHelper.updateProviderStartTime(screenState.value.startTimeHoursMinutes)
                    dataStoreHelper.updateProviderScheduleData(gson.toJson(providerSchedule))
                    dataStoreHelper.updateShowProviderStartTime(true)
                    updateToastMessage()
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
                                    providerImage = it1.medium,
                                    providerName = "$firstName $lastName",
                                    providerId = it2.value
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
                    Log.d("NETWORK_ERROR", "getApiData: Network Error")
                    _progressState.update { UIState.Success }
                }

                is MainResponse.UnknownError -> {
                    Log.d("UNKNOWN_ERROR", "getApiData: Unknown Error")
                    _progressState.update { UIState.Success }
                }
            }
        }
    }

    private fun getInitialData() {
        viewModelScope.launch {
            _screenState.update {
                it.copy(
                    showWorkDay = mutableStateOf(dataStoreHelper.getShowMonthDayYear()),
                    monthDayYear = dataStoreHelper.getUserData(),
                    showStartTime = mutableStateOf(dataStoreHelper.getShowProviderStartTime()),
                    startTimeHoursMinutes = dataStoreHelper.getProviderStartTime(),
                    showEndTime = mutableStateOf(dataStoreHelper.getShowProviderEndTime()),
                    endTimeHoursMinutes = dataStoreHelper.getProviderEndTime()
                )
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
}