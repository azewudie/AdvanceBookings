package com.aaron.advancebookings.utilities.helper

import com.aaron.advancebookings.data.local.PreferenceDataStoreAPI
import com.aaron.advancebookings.data.local.PreferenceDataStoreKey
import com.aaron.advancebookings.data.remote.models.responses.DefaultJsonValue
import com.aaron.advancebookings.utilities.constants.AppConstants
import javax.inject.Inject

class StoreHelper
@Inject
constructor(
    private val dataStore: PreferenceDataStoreAPI
) {
    suspend fun updateUserData(data: String) {
        dataStore.putPreference(PreferenceDataStoreKey.USER_DATA, data)
    }

    suspend fun getUserData(): String {
        return dataStore.getPreference(
            PreferenceDataStoreKey.USER_DATA, AppConstants.EMPTY_STRING
        )
    }

    suspend fun updateShowMonthDayYear(show: Boolean) {
        dataStore.putPreference(PreferenceDataStoreKey.SHOW_MONTH_DAY_YEAR, show)
    }

    suspend fun getShowMonthDayYear(): Boolean {
        return dataStore.getPreference(
            PreferenceDataStoreKey.SHOW_MONTH_DAY_YEAR, false
        )
    }


    suspend fun updateProviderStartTime(data: String) {
        dataStore.putPreference(PreferenceDataStoreKey.PROVIDER_START_TIME, data)
    }

    suspend fun getProviderStartTime(): String {
        return dataStore.getPreference(
            PreferenceDataStoreKey.PROVIDER_START_TIME, AppConstants.EMPTY_STRING
        )
    }

    suspend fun updateProviderEndTime(data: String) {
        dataStore.putPreference(PreferenceDataStoreKey.PROVIDER_END_TIME, data)
    }

    suspend fun getProviderEndTime(): String {
        return dataStore.getPreference(
            PreferenceDataStoreKey.PROVIDER_END_TIME, AppConstants.EMPTY_STRING
        )
    }

    suspend fun updateShowProviderStartTime(show: Boolean) {
        dataStore.putPreference(PreferenceDataStoreKey.SHOW_PROVIDER_START_TIME, show)
    }

    suspend fun getShowProviderStartTime(): Boolean {
        return dataStore.getPreference(
            PreferenceDataStoreKey.SHOW_PROVIDER_START_TIME, false
        )
    }

    suspend fun updateShowProviderEndTime(show: Boolean) {
        dataStore.putPreference(PreferenceDataStoreKey.SHOW_PROVIDER_END_TIME, show)
    }

    suspend fun getShowProviderEndTime(): Boolean {
        return dataStore.getPreference(
            PreferenceDataStoreKey.SHOW_PROVIDER_END_TIME, false
        )
    }


    suspend fun updateClientReservationData(data: String) {
        dataStore.putPreference(PreferenceDataStoreKey.CLIENT_RESERVATION, data)
    }

    suspend fun getClientReservationData(): String {
        return dataStore.getPreference(
            PreferenceDataStoreKey.CLIENT_RESERVATION, DefaultJsonValue.defaultClientReservationJsonValue
        )
    }


    suspend fun updateProviderScheduleData(data: String) {
        dataStore.putPreference(PreferenceDataStoreKey.PROVIDER_SCHEDULE, data)
    }

    suspend fun getProviderScheduleData(): String {
        return dataStore.getPreference(
            PreferenceDataStoreKey.PROVIDER_SCHEDULE, DefaultJsonValue.defaultProviderScheduleJsonValue
        )
    }


    suspend fun updateIsClientReserveTimeSlot(show: Boolean) {
        dataStore.putPreference(PreferenceDataStoreKey.IS_CLIENT_RESERVE_TIME_SLOT, show)
    }

    suspend fun getIsClientReserveTimeSlot(): Boolean {
        return dataStore.getPreference(
            PreferenceDataStoreKey.IS_CLIENT_RESERVE_TIME_SLOT, false
        )
    }


    suspend fun updateClientReserveTime(data: String) {
        dataStore.putPreference(PreferenceDataStoreKey.CLIENT_RESERVE_TIME_SLOT, data)
    }

    suspend fun getClientReserveTime(): String {
        return dataStore.getPreference(
            PreferenceDataStoreKey.CLIENT_RESERVE_TIME_SLOT, AppConstants.EMPTY_STRING
        )
    }

    suspend fun updateClientReserveRealTime(data: String) {
        dataStore.putPreference(PreferenceDataStoreKey.CLIENT_RESERVE_REAL_TIME, data)
    }

    suspend fun getClientReserveRealTime(): String {
        return dataStore.getPreference(
            PreferenceDataStoreKey.CLIENT_RESERVE_REAL_TIME, AppConstants.EMPTY_STRING
        )
    }

}