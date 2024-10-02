package com.aaron.advancebookings.ui.screens.clientscreeen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aaron.advancebookings.R
import com.aaron.advancebookings.ui.common.compose.CommonButton
import com.aaron.advancebookings.ui.common.compose.CommonImageHeaderDescriptionCard
import com.aaron.advancebookings.ui.common.compose.CommonText
import com.aaron.advancebookings.ui.common.compose.CustomToast
import com.aaron.advancebookings.ui.common.composeattributes.TextAttributes
import com.aaron.advancebookings.ui.common.screenstate.UIState
import com.aaron.advancebookings.ui.theme.CustomTheme
import com.aaron.advancebookings.utilities.constants.AppConstants
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ClientScreen(
    progressState: State<UIState>,
    screeState: State<ClientScreenUIStates>,
    onEvent: (ClientScreenUIEvents) -> Unit
) {
    CustomTheme {
        LaunchedEffect(true) {
            onEvent.invoke(ClientScreenUIEvents.GetInitialData)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomTheme.colors.cardDefault),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CustomTheme.colors.cardDefault),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (screeState.value.showToastMessage.value) {
                    CustomToast(
                        messageType = screeState.value.toastMessageType,
                        message = screeState.value.toastMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = CustomTheme.spaces.dp10)
                    )
                }

                Column(
                    modifier = Modifier
                        .background(CustomTheme.colors.cardDefault)
                        .padding(
                            start = CustomTheme.spaces.dp16,
                            end = CustomTheme.spaces.dp16,
                            bottom = CustomTheme.spaces.dp16
                        ),
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(CustomTheme.spaces.dp16)
                    )
                    CommonImageHeaderDescriptionCard(
                        cardImage = screeState.value.clientImage,
                        fullName = screeState.value.clientName,
                        team = screeState.value.clientId ?: AppConstants.EMPTY_STRING
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(CustomTheme.spaces.dp16)
                    )
                    if (screeState.value.showSelectDayButton.value) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = CustomTheme.spaces.dp16)
                        ) {
                            CommonButton(
                                buttonTitle = screeState.value.selectDayButtonLabel,
                            ) {
                                onEvent.invoke(ClientScreenUIEvents.OnClickSelectDay)
                            }
                        }
                    }
                    if (screeState.value.showUserSelectedTimeSlot.value) {
                        Column(
                            modifier = Modifier
                                .background(CustomTheme.colors.logoColor)
                                .padding(CustomTheme.spaces.dp16)
                        ) {
                            CommonText(
                                TextAttributes(
                                    text = "Your up coming Schedule: ${screeState.value.userSchedule}",
                                    textColor = CustomTheme.colors.textWhite,
                                    textAlign = TextAlign.Center,
                                    textStyle = MaterialTheme.typography.bodyLarge
                                )
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(CustomTheme.spaces.dp16)
                        )
                        if (screeState.value.showConfirmReservationButtonLabel.value) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = CustomTheme.spaces.dp16)
                            ) {
                                CommonButton(
                                    buttonTitle = screeState.value.confirmReservationButtonLabel,
                                    color = CustomTheme.colors.statusError1
                                ) {
                                    onEvent.invoke(ClientScreenUIEvents.OnConfirmSelectedTime)
                                }
                            }
                        }
                    }
                    if (screeState.value.showAvailableTimeSlot.value) {
                        CommonText(
                            TextAttributes(
                                text = "Available time slat",
                                textColor = CustomTheme.colors.text0,
                                textAlign = TextAlign.Center,
                                textStyle = MaterialTheme.typography.headlineLarge
                            )
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(CustomTheme.spaces.dp16)
                        )

                        TimeGridSelection(
                            startHour = screeState.value.providerStartHours,
                            startMinute = screeState.value.providerStartMinutes,
                            isStartAm = screeState.value.providerStartAM,
                            endHour = screeState.value.providerEndHours,
                            endMinute = screeState.value.providerEndMinutes,
                            isEndAm = screeState.value.providerEndPM,
                        ) { selectedTimeSlot, startTime, endTime, currentDayTime ->
                            onEvent.invoke(
                                ClientScreenUIEvents
                                    .OnClickAvailableTime(
                                        selectedTime = selectedTimeSlot,
                                        startTime = startTime,
                                        endTime = endTime,
                                        currentDayTime = currentDayTime
                                    )
                            )
                        }
                    }
                    if (screeState.value.showNotAvailableTime.value) {
                        Column(
                            modifier = Modifier
                                .padding(CustomTheme.spaces.dp16)
                        ) {
                            CommonText(
                                TextAttributes(
                                    text = "Unfortunately, we do not have availability on the selected date. Please choose an alternative date. Thank you for your understanding!",
                                    textColor = CustomTheme.colors.text0,
                                    textAlign = TextAlign.Center,
                                    textStyle = MaterialTheme.typography.bodyLarge
                                )
                            )
                        }
                    }
                    CalendarDialogSection(
                        showCalenderDialog = screeState.value.showCalendarDialog,
                        onUserPickRightDay = { selectedDay ->
                            onEvent.invoke(
                                ClientScreenUIEvents.OnBookingSuccess(
                                    selectedDay
                                )
                            )
                        },
                        onUserPickWrongDay = {
                            onEvent.invoke(ClientScreenUIEvents.OnDateSelectedWrong)
                        }
                    )

                }
            }

            if (progressState.value == UIState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDialogSection(
    showCalenderDialog: MutableState<Boolean> = mutableStateOf(false),
    onUserPickRightDay: (String) -> Unit,
    onUserPickWrongDay: () -> Unit,
) {
    CustomTheme {
        val calendarState = rememberSheetState()
        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true,
            ),
            selection = CalendarSelection.Date { selectedDate ->
                if (isSelectableDate(selectedDate)) {
                    val day = selectedDate.dayOfMonth
                    val month = selectedDate.monthValue
                    val year = selectedDate.year

                    Log.d(
                        "Book head of time",
                        "Selected Date: Day = $day, Month = $month, Year = $year"
                    )
                    onUserPickRightDay("$month/$day/$year")
                } else {
                    onUserPickWrongDay()
                }

            },

            )
        if (showCalenderDialog.value) {
            calendarState.show()

        }
    }
}

fun isSelectableDate(date: LocalDate): Boolean {
    // Get the current date and time
    val now = LocalDateTime.now(ZoneId.systemDefault())
    // Get the date and time for the next selectable date (24 hours from now)
    val nextSelectableDate = now.plusHours(24).toLocalDate() // Get the date part only

    return !date.isBefore(nextSelectableDate) // Allow selection from the next selectable date onward
}

@Composable
fun TimeGridSelection(
    startHour: Int,
    startMinute: Int,
    isStartAm: Boolean,
    endHour: Int,
    endMinute: Int,
    isEndAm: Boolean,
    onClick: (String, String, String, String) -> Unit
) {
    // List of time slots (strings with AM/PM format)
    val timeSlots = generateTimeSlots(
        startHour = startHour,
        startMinute = startMinute,
        isStartAm = isStartAm,
        endHour = endHour,
        endMinute = endMinute,
        isEndAm = isEndAm

    )

    // State to track the selected time
    var selectedTime by remember { mutableStateOf<String?>(null) }

    // Grid layout
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),  // Adjust the column count based on design preference
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(timeSlots.size / 2) { index ->
            val startTime = timeSlots[index * 2] // Get the start time
            val endTime = timeSlots.getOrNull(index * 2 + 1) // Get th
            val time = "$startTime - $endTime"
            TimeSlotItem(
                time = time,
                isSelected = selectedTime == time,
                onClick = {
                    // Get current time
                    val currentTime = LocalDateTime.now()
                    selectedTime = time
                    onClick(time, startTime, endTime ?: "", currentTime.toString())
                }
            )
        }
    }
}

@Composable
fun TimeSlotItem(
    time: String, // Now we use String for the formatted time with AM/PM
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Time slot UI
    Surface(
        modifier = Modifier
            .size(80.dp)
            .clickable(onClick = onClick),
        color = if (isSelected) MaterialTheme.colorScheme.primary else CustomTheme.colors.logoColor,
        shape = MaterialTheme.shapes.medium
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = time, // Display the time string (formatted with AM/PM)
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else CustomTheme.colors.textWhite
            )
        }
    }
}


// Function to generate time slots in 15-minute intervals with AM/PM
fun generateTimeSlots(
    startHour: Int,
    startMinute: Int,
    isStartAm: Boolean,
    endHour: Int,
    endMinute: Int,
    isEndAm: Boolean
): List<String> {
    // Convert hours to 24-hour format based on AM/PM
    val adjustedStartHour = if (isStartAm) {
        if (startHour == 12) 0 else startHour // 12 AM is 0 hours
    } else {
        if (startHour == 12) 12 else startHour + 12 // 12 PM is 12 hours, others are +12
    }

    val adjustedEndHour = if (isEndAm) {
        if (endHour == 12) 0 else endHour // 12 AM is 0 hours
    } else {
        if (endHour == 12) 12 else endHour + 12 // 12 PM is 12 hours, others are +12
    }

    val startTime = LocalTime.of(adjustedStartHour, startMinute)
    val endTime = LocalTime.of(adjustedEndHour, endMinute)

    val timeSlots = mutableListOf<String>()
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a") // 12-hour format with AM/PM
    var currentTime = startTime

    // Handle overnight time range
    val actualEndTime = if (endTime.isBefore(startTime)) {
        endTime.plusHours(24) // If end time is before start time, assume it's on the next day
    } else {
        endTime
    }

    // Generate time slots in 15-minute intervals
    while (currentTime.isBefore(actualEndTime) || currentTime == actualEndTime) {
        timeSlots.add(currentTime.format(timeFormatter))
        currentTime = currentTime.plusMinutes(15)
    }

    return timeSlots
}

@Composable
@Preview(
    name = "Light Mode",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
private fun PreviewClientScreen() {
    CustomTheme {
        ClientScreen(
            progressState = flow<UIState> {}.collectAsState(initial = UIState.Success),
            screeState = flow<ClientScreenUIStates> {}.collectAsState(
                initial = ClientScreenUIStates(
                    confirmReservationButtonLabel = stringResource(R.string.confirm_button_label),
                    availableTimeSlot = stringResource(R.string.available_time_slot),
                    selectDayButtonLabel = stringResource(R.string.select_day)
                )
            ),
            onEvent = {}
        )
    }
}


