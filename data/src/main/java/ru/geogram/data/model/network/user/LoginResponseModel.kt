package ru.geogram.data.model.network.user

import com.google.gson.annotations.SerializedName
import ru.geogram.data.model.network.ResponseError

data class LoginResponseModel(
        @SerializedName("data")
    var data: Data? = null,
        @SerializedName("error")
    var error: ResponseError? = null
)