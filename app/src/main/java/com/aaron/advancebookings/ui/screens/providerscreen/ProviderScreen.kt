package com.aaron.advancebookings.ui.screens.providerscreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.aaron.advancebookings.R
import com.aaron.advancebookings.ui.common.compose.CommonButton
import com.aaron.advancebookings.ui.common.compose.CommonDotListComponent
import com.aaron.advancebookings.ui.common.compose.CommonImageHeaderDescriptionCard
import com.aaron.advancebookings.ui.common.compose.CommonText
import com.aaron.advancebookings.ui.common.compose.CustomToast
import com.aaron.advancebookings.ui.common.composeattributes.TextAttributes
import com.aaron.advancebookings.ui.common.screenstate.UIState

import com.aaron.advancebookings.ui.theme.CustomTheme
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

@Composable
fun ProviderScreen(
    progressState: State<UIState>,
    screeState: State<ProviderScreenUIStates>,
    onEvent: (ProviderScreenUIEvents) -> Unit
) {
    CustomTheme {
        LaunchedEffect(true) {
            onEvent.invoke(ProviderScreenUIEvents.GetInitialData)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomTheme.colors.cardDefault),
            horizontalAlignment = Alignment.CenterHorizontally,

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
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = CustomTheme.spaces.dp16,
                        end = CustomTheme.spaces.dp16,
                        bottom = CustomTheme.spaces.dp100
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(CustomTheme.spaces.dp16)
                )
                screeState.value.providerId?.let {
                    CommonImageHeaderDescriptionCard(
                        cardImage = screeState.value.providerImage,
                        fullName = screeState.value.providerName,
                        team = it
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(CustomTheme.spaces.dp16)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = CustomTheme.spaces.dp16),
                ) {
                    CommonButton(
                        buttonTitle = screeState.value.pickDay
                    ) {
                        onEvent.invoke(ProviderScreenUIEvents.OnClickPickDay)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = CustomTheme.spaces.dp16),
                ) {
                    CommonButton(
                        buttonTitle = screeState.value.pickStartTime
                    ) {
                        onEvent.invoke(ProviderScreenUIEvents.OnClickPickStartTime)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = CustomTheme.spaces.dp16),
                ) {
                    CommonButton(
                        buttonTitle = screeState.value.pickEndTime
                    ) {
                        onEvent.invoke(ProviderScreenUIEvents.OnClickPickEndTime)
                    }
                }

                Column(
                    modifier = Modifier.padding(CustomTheme.spaces.dp16)
                ) {
                    AnimatedVisibility(screeState.value.showWorkDay.value) {
                        CommonDotListComponent(content = screeState.value.monthDayYear)
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(CustomTheme.spaces.dp16)
                    )
                    AnimatedVisibility(screeState.value.showStartTime.value) {
                        CommonDotListComponent(content = screeState.value.startTimeHoursMinutes)
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(CustomTheme.spaces.dp16)
                    )
                    AnimatedVisibility(screeState.value.showEndTime.value) {
                        CommonDotListComponent(content = screeState.value.endTimeHoursMinutes)
                    }

                }
                CalendarDialogSection(
                    showCalenderDialog = screeState.value.showCalendarDialog,
                    onUserPickRightDay = { workDay ->
                        onEvent.invoke(ProviderScreenUIEvents.OnWorkDayBookingSuccess(workDay = workDay))
                    }
                ) {
                    onEvent.invoke(ProviderScreenUIEvents.OnDateSelectedWrong)
                }
                TimePickerDialogSection(
                    showTimePickerDialog = screeState.value.showTimePickerDialog
                ) {startTime->
                    onEvent.invoke(ProviderScreenUIEvents.OnStartTimePicking(startTime = startTime))
                }
                TimePickerDialogSection(
                    showTimePickerDialog = screeState.value.showEndTimePickerDialog
                ) { endTime ->
                    onEvent.invoke(ProviderScreenUIEvents.OnEndTimePicking(endTime = endTime))
                }

                if(screeState.value.isSomeOneBookTime.value){
                    UpComingScheduleWithClient(screeState.value.upcomingClientSchedule)
                }


                if (progressState.value == UIState.Loading) {
                    CircularProgressIndicator()
                }

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
                if (isSelectableDateFromCurrentDay(selectedDate)) {
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
@Composable
fun UpComingScheduleWithClient(content:String){
    CustomTheme {
        Column(
            modifier = Modifier.padding(CustomTheme.spaces.dp16),
            horizontalAlignment = Alignment.Start
        ){
            CommonText(
                TextAttributes(
                    text = "Your Upcoming Schedule With Client",
                    textColor = CustomTheme.colors.text0,
                    textStyle = CustomTheme.typography.titleBold,
                    modifier = Modifier.padding(bottom = CustomTheme.spaces.dp16)
                )
            )
            CommonDotListComponent(content = content)


        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogSection(
    showTimePickerDialog: MutableState<Boolean> = mutableStateOf(false),
    onUserPickTime: (String) -> Unit,
) {
    CustomTheme {
        val clockState = rememberSheetState()
        ClockDialog(
            state = clockState,
            config = ClockConfig(
                is24HourFormat = false,
                defaultTime = LocalTime.now()
            ),
            selection = ClockSelection.HoursMinutes { hours, minutes ->
                val amPm = if (hours < 12) "AM" else "PM"
                val adjustedHours = if (hours > 12) hours - 12 else if (hours == 0) 12 else hours
                println("Selected time: $adjustedHours:$minutes $amPm")
                onUserPickTime("$adjustedHours:$minutes $amPm")
            }
        )
        if (showTimePickerDialog.value) {
            clockState.show()
        }
    }
}

fun isSelectableDateFromCurrentDay(date: LocalDate): Boolean {
    // Get the current date
    val today = LocalDate.now(ZoneId.systemDefault())

    // Allow selection from today onward (including today)
    return !date.isBefore(today)
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
private fun PreviewProviderScreen() {
    CustomTheme {
        ProviderScreen(
            progressState = flow<UIState> {}.collectAsState(initial = UIState.Success),
            screeState = flow<ProviderScreenUIStates> {}.collectAsState(
                initial = ProviderScreenUIStates(
                    pickDay = stringResource(R.string.provide_pick_day),
                    pickStartTime = stringResource(R.string.provide_pick_time_start),
                    pickEndTime = stringResource(R.string.provide_pick_time_end)
                )
            ),
            onEvent = {}
        )
    }
}