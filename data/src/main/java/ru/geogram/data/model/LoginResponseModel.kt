package ru.geogram.data.model

import com.google.gson.annotations.SerializedName
import ru.geogram.data.model.Data
import ru.geogram.data.model.LoginResponseError

data class LoginResponseModel(
    @SerializedName("data")
    var data: Data? = null,
    @SerializedName("error")
    var error: LoginResponseError? = null
)