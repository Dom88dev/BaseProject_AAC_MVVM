package com.project.base.model.remote.domain

import com.google.gson.annotations.SerializedName

data class MainInfo(
    @SerializedName("temp") val temperature: Float,
    @SerializedName("feels_like") val feelTemp: Float,
    @SerializedName("temp_max") val maxTemp: Float,
    @SerializedName("temp_min") val minTemp: Float,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humid: Int,
    @SerializedName("sea_level") val seaLv: Int,
    @SerializedName("grnd_level") val groundLv: Int
)