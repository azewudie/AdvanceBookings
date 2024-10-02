package com.aaron.advancebookings.data

import com.aaron.advancebookings.data.local.PreferenceDataStoreAPI
import com.aaron.advancebookings.data.remote.MainDataManager
import com.aaron.advancebookings.data.remote.service.RandomUserAPIs

abstract class DataRepository {
    abstract fun getNetworkDataManager(): MainDataManager
    abstract fun getApplicationApiService(): RandomUserAPIs
    abstract fun getAppDataStore():PreferenceDataStoreAPI
}