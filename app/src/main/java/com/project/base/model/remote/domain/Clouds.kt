package com.project.base.model.remote.domain

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all") val cloudiness: Int
)