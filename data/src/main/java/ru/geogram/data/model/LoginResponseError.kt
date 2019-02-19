package ru.geogram.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponseError(
    @SerializedName("code")
    var code: String,
    @SerializedName("description")
    var description: String
)