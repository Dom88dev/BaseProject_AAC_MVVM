package com.project.base.model.remote.domain

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id") val weatherConditionId: Int,
    @SerializedName("main") val weatherGroup: String,
    @SerializedName("description") val weatherDescription: String,
    @SerializedName("icon") val weatherIcon: String
)