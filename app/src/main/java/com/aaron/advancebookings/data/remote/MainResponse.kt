package com.aaron.advancebookings.data.remote

import okio.IOException

sealed class MainResponse<out T : Any, out u : Any> {
    /**
     * API Success
     */
    data class Success<T : Any>(
        val body: T,
    ) : MainResponse<T, Nothing>()

    /**
     * API Failure
     */
    data class ApiError<U : Any>(
        val body: U,
        val code: Int,
    ) : MainResponse<Nothing, U>()

    /**
     * Network error
     */

    data class NetworkError(
        val error: IOException,
    ) : MainResponse<Nothing, Nothing>()

    /**
     * json parsing error
     */
    data class UnknownError(
        val error: Throwable?
    ) : MainResponse<Nothing, Nothing>()


}
