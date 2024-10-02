package com.aaron.advancebookings.data.remote

import retrofit2.Retrofit
class MainDataManager(
    private var tokenRetrofit: Retrofit,
) {
    fun getTokenClint(): Retrofit = tokenRetrofit
}