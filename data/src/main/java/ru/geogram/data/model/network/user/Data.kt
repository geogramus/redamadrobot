package ru.geogram.data.model.network.user

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("user")
    var user: UserResponse? = null
)