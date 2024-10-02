package com.aaron.advancebookings.data.remote.service

import com.aaron.advancebookings.data.remote.ApiEndPoints
import com.aaron.advancebookings.data.remote.MainResponse
import com.aaron.advancebookings.data.remote.models.responses.CustomerResponse
import com.aaron.advancebookings.data.remote.models.responses.ResponseError
import retrofit2.http.GET

interface RandomUserAPIs {
    @GET(ApiEndPoints.RANDOM_USER)
    suspend fun getRandomUser(): MainResponse<CustomerResponse, ResponseError>
}