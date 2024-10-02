package com.aaron.advancebookings.data.remote

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

internal class MainResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>,
) : Call<MainResponse<S, E>> {
    override fun enqueue(callback: Callback<MainResponse<S, E>>) =
        delegate.enqueue(
            object : Callback<S> {
                override fun onResponse(
                    call: Call<S>,
                    response: Response<S>,
                ) {
                    val body = response.body()
                    val code = response.code()
                    val error = response.errorBody()

                    if (response.isSuccessful) {
                        if (body != null) {
                            callback.onResponse(
                                this@MainResponseCall,
                                Response.success(MainResponse.Success(body)),
                            )
                        } else {
                            // Response is successful but the body is null
                            callback.onResponse(
                                this@MainResponseCall,
                                Response.success(MainResponse.UnknownError(null)),
                            )
                        }
                    } else {
                        val errorBody =
                            when {
                                error == null -> null
                                error.contentLength() == 0L -> null
                                else ->
                                    try {
                                        errorConverter.convert(error)
                                    } catch (ex: Exception) {
                                        null
                                    }
                            }
                        if (errorBody != null) {
                            callback.onResponse(
                                this@MainResponseCall,
                                Response.success(MainResponse.ApiError(errorBody, code)),
                            )
                        } else {
                            callback.onResponse(
                                this@MainResponseCall,
                                Response.success(MainResponse.UnknownError(null)),
                            )
                        }
                    }
                }

                override fun onFailure(
                    call: Call<S>,
                    throwable: Throwable,
                ) {
                    val networkResponse =
                        when (throwable) {
                            is IOException -> MainResponse.NetworkError(throwable)
                            else -> MainResponse.UnknownError(throwable)
                        }
                    callback.onResponse(this@MainResponseCall, Response.success(networkResponse))
                }
            },
        )

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = MainResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<MainResponse<S, E>> =
        throw UnsupportedOperationException(
            "NetworkResponseCall doesn't support execute",
        )

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
