package com.aaron.advancebookings.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceDataStoreKey {
    val USER_DATA = stringPreferencesKey("user_data")
    val SHOW_MONTH_DAY_YEAR = booleanPreferencesKey("show_month_day_year")
    val PROVIDER_START_TIME = stringPreferencesKey("provider_start_time")
    val SHOW_PROVIDER_START_TIME = booleanPreferencesKey("show_provider_start_time")
    val PROVIDER_END_TIME = stringPreferencesKey("provider_end_time")
    val SHOW_PROVIDER_END_TIME = booleanPreferencesKey("show_provider_end_time")
    val PROVIDER_SCHEDULE = stringPreferencesKey("provider_schedule")
    val CLIENT_RESERVATION = stringPreferencesKey("client_reservation")
    val IS_CLIENT_RESERVE_TIME_SLOT = booleanPreferencesKey("is_client_reserve_time_slot")
    val CLIENT_RESERVE_TIME_SLOT = stringPreferencesKey("client_reserve_time_slot")
    val CLIENT_RESERVE_REAL_TIME = stringPreferencesKey("client_reserve_real_time")
}