package com.project.base.model.remote.response

import com.google.gson.annotations.SerializedName
import com.project.base.model.remote.domain.*

data class WeatherResponse(
    @SerializedName("coord") val coord: Coordinates,
    @SerializedName("weather") val weathers: List<Weather>,
    @SerializedName("base") val base: String,
    @SerializedName("main") val main: MainInfo,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("clouds") val clouds: Clouds?,
    @SerializedName("rain") val rain: Rain?,
    @SerializedName("snow") val snow: Snow?,
    @SerializedName("dt") val timeOfData: Long,
    @SerializedName("sys") val sys: SysInfo,
    @SerializedName("timezone") val timezone: Long,
    @SerializedName("id") val cityId: Long,
    @SerializedName("name") val cityName: String,
    @SerializedName("cod") val cod: Int
)