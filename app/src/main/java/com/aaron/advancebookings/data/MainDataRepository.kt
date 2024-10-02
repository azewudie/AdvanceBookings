package com.aaron.advancebookings.data

import com.aaron.advancebookings.data.local.PreferenceDataStoreAPI
import com.aaron.advancebookings.data.remote.MainDataManager
import com.aaron.advancebookings.data.remote.service.RandomUserAPIs
import retrofit2.Retrofit
import retrofit2.create

class MainDataRepository(
    private var tokenRetrofit: Retrofit,
    private var dataStore:PreferenceDataStoreAPI
) : DataRepository() {
    override fun getNetworkDataManager(): MainDataManager =
        MainDataManager(
            tokenRetrofit = tokenRetrofit
        )

    override fun getApplicationApiService(): RandomUserAPIs =
        getNetworkDataManager()
            .getTokenClint()
            .create<RandomUserAPIs>()

    override fun getAppDataStore(): PreferenceDataStoreAPI = dataStore

}