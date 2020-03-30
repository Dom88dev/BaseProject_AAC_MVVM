package com.project.base.model.remote.domain

import com.google.gson.annotations.SerializedName

data class SysInfo(
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)