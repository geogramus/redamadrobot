package ru.geogram.data.model.network

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("code")
    var code: String,
    @SerializedName("description")
    var description: String
)