package ru.geogram.data.model.network.user

import com.google.gson.annotations.SerializedName
import ru.geogram.data.model.network.user.UserResponse

data class Data(
    @SerializedName("user")
    var user: UserResponse? = null
)