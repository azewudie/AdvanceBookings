package com.aaron.advancebookings.ui.screens.clientscreeen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.aaron.advancebookings.ui.common.compose.MessageType
import com.aaron.advancebookings.utilities.constants.AppConstants

data class ClientScreenUIStates(
    val clientImage: String = AppConstants.EMPTY_STRING,
    val clientName: String = AppConstants.EMPTY_STRING,
    val clientId: String? = AppConstants.EMPTY_STRING,
    val toastMessageType: MessageType = MessageType.MessageSuccess,
    val toastMessage: String = AppConstants.EMPTY_STRING,
    val showToastMessage: MutableState<Boolean> = mutableStateOf(false),
    val showAvailableTimeSlot: MutableState<Boolean> = mutableStateOf(false),
    val showUserSelectedTimeSlot: MutableState<Boolean> = mutableStateOf(false),
    val availableTimeSlot: String = AppConstants.EMPTY_STRING,
    val userSchedule: String = AppConstants.EMPTY_STRING,
    val confirmReservationButtonLabel: String = AppConstants.EMPTY_STRING,
    val showConfirmReservationButtonLabel: MutableState<Boolean> = mutableStateOf(false),
    val showConfirmationToast: MutableState<Boolean> = mutableStateOf(false),
    val showSelectDayButton: MutableState<Boolean> = mutableStateOf(false),
    val selectDayButtonLabel: String = AppConstants.EMPTY_STRING,
    val showCalendarDialog: MutableState<Boolean> = mutableStateOf(false),
    val providerAvailableDay: String = AppConstants.EMPTY_STRING,
    val showNotAvailableTime: MutableState<Boolean> = mutableStateOf(false),
    val providerStartHours: Int = Int.MIN_VALUE,
    val providerStartMinutes: Int = Int.MIN_VALUE,
    val providerStartAM: Boolean = false,
    val providerEndHours: Int = Int.MIN_VALUE,
    val providerEndMinutes: Int = Int.MIN_VALUE,
    val providerEndPM: Boolean = false,
    )