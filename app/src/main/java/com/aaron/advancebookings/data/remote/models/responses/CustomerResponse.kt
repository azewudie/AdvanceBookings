package com.aaron.advancebookings.data.remote.models.responses

import com.aaron.advancebookings.utilities.constants.AppConstants
import com.google.gson.annotations.SerializedName

data class CustomerResponse(
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("results")
    val results: List<Result> = emptyList()
)

data class Coordinates(
    @SerializedName("latitude")
    val latitude: String = AppConstants.EMPTY_STRING,
    @SerializedName("longitude")
    val longitude: String = AppConstants.EMPTY_STRING
)

data class Dob(
    @SerializedName("age")
    val age: Int = Int.MIN_VALUE,
    @SerializedName("date")
    val date: String = AppConstants.EMPTY_STRING
)

data class Id(
    @SerializedName("name")
    val name: String = AppConstants.EMPTY_STRING,
    @SerializedName("value")
    val value: String = AppConstants.EMPTY_STRING
)

data class Info(
    @SerializedName("page")
    val page: Int = Int.MIN_VALUE,
    @SerializedName("results")
    val results: Int = Int.MIN_VALUE,
    @SerializedName("seed")
    val seed: String = AppConstants.EMPTY_STRING,
    @SerializedName("version")
    val version: String = AppConstants.EMPTY_STRING
)

data class Location(
    @SerializedName("city")
    val city: String = AppConstants.EMPTY_STRING,
    @SerializedName("coordinates")
    val coordinates: Coordinates,
    @SerializedName("country")
    val country: String = AppConstants.EMPTY_STRING,
    @SerializedName("postcode")
    val postcode: Int = Int.MIN_VALUE,
    @SerializedName("state")
    val state: String = AppConstants.EMPTY_STRING,
    @SerializedName("street")
    val street: Street? = null,
    @SerializedName("timezone")
    val timezone: Timezone? = null
)

data class Login(
    @SerializedName("md5")
    val md5: String = AppConstants.EMPTY_STRING,
    @SerializedName("password")
    val password: String = AppConstants.EMPTY_STRING,
    @SerializedName("salt")
    val salt: String = AppConstants.EMPTY_STRING,
    @SerializedName("sha1")
    val sha1: String = AppConstants.EMPTY_STRING,
    @SerializedName("sha256")
    val sha256: String = AppConstants.EMPTY_STRING,
    @SerializedName("username")
    val username: String = AppConstants.EMPTY_STRING,
    @SerializedName("uuid")
    val uuid: String = AppConstants.EMPTY_STRING
)

data class Name(
    @SerializedName("first")
    val first: String = AppConstants.EMPTY_STRING,
    @SerializedName("last")
    val last: String = AppConstants.EMPTY_STRING,
    @SerializedName("title")
    val title: String = AppConstants.EMPTY_STRING
)

data class Picture(
    @SerializedName("large")
    val large: String = AppConstants.EMPTY_STRING,
    @SerializedName("medium")
    val medium: String = AppConstants.EMPTY_STRING,
    @SerializedName("thumbnail")
    val thumbnail: String = AppConstants.EMPTY_STRING
)

data class Registered(
    @SerializedName("age")
    val age: Int = Int.MIN_VALUE,
    @SerializedName("date")
    val date: String = AppConstants.EMPTY_STRING
)

data class Result(
    @SerializedName("cell")
    val cell: String = AppConstants.EMPTY_STRING,
    @SerializedName("dob")
    val dob: Dob? = null,
    @SerializedName("email")
    val email: String = AppConstants.EMPTY_STRING,
    @SerializedName("gender")
    val gender: String = AppConstants.EMPTY_STRING,
    @SerializedName("id")
    val id: Id? = null,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("login")
    val login: Login? = null,
    @SerializedName("name")
    val name: Name? = null,
    @SerializedName("nat")
    val nat: String = AppConstants.EMPTY_STRING,
    @SerializedName("phone")
    val phone: String = AppConstants.EMPTY_STRING,
    @SerializedName("picture")
    val picture: Picture? = null,
    @SerializedName("registered")
    val registered: Registered? = null
)

data class Street(
    @SerializedName("name")
    val name: String = AppConstants.EMPTY_STRING,
    @SerializedName("number")
    val number: Int = Int.MIN_VALUE
)

data class Timezone(
    @SerializedName("description")
    val description: String = AppConstants.EMPTY_STRING,
    @SerializedName("offset")
    val offset: String = AppConstants.EMPTY_STRING
)