package com.aaron.advancebookings.ui.screens.providerscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.aaron.advancebookings.ui.common.compose.MessageType
import com.aaron.advancebookings.utilities.constants.AppConstants

data class ProviderScreenUIStates(
    val providerImage:String = AppConstants.EMPTY_STRING,
    val providerName:String = AppConstants.EMPTY_STRING,
    val providerId:String? = AppConstants.EMPTY_STRING,
    val toastMessageType: MessageType = MessageType.MessageSuccess,
    val toastMessage:String = AppConstants.EMPTY_STRING,
    val showToastMessage: MutableState<Boolean> = mutableStateOf(false),
    val showCalendarDialog: MutableState<Boolean> = mutableStateOf(false),
    val showTimePickerDialog: MutableState<Boolean> = mutableStateOf(false),
    val showEndTimePickerDialog: MutableState<Boolean> = mutableStateOf(false),
    val pickDay:String = AppConstants.EMPTY_STRING,
    val pickStartTime:String = AppConstants.EMPTY_STRING,
    val pickEndTime:String = AppConstants.EMPTY_STRING,

    val showWorkDay:MutableState<Boolean> = mutableStateOf(false),
    val monthDayYear:String = AppConstants.EMPTY_STRING,

    val showStartTime:MutableState<Boolean> = mutableStateOf(false),
    val startTimeHoursMinutes:String = AppConstants.EMPTY_STRING,

    val showEndTime:MutableState<Boolean> = mutableStateOf(false),
    val endTimeHoursMinutes:String = AppConstants.EMPTY_STRING,
    val upcomingClientSchedule:String = AppConstants.EMPTY_STRING,
    val isSomeOneBookTime:MutableState<Boolean> = mutableStateOf(false),


)