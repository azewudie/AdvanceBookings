package com.aaron.advancebookings.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class MainResponseAdapter<S:Any,E:Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody,E>
):CallAdapter<S, Call<MainResponse<S, E>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<MainResponse<S, E>> =
        MainResponseCall(
            call,
            errorBodyConverter
        )
}