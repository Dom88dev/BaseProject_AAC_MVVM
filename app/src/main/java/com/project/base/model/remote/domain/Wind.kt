package com.project.base.model.remote.domain

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") val speed: Float,
    @SerializedName("deg") val degrees: Int
)