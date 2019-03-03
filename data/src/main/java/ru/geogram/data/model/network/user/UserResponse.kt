package ru.geogram.data.model.network.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("first_name")
    var first_name: String? = "",
    @SerializedName("role")
    var role: String? = "",
    @SerializedName("last_name")
    var last_name: String? = "",
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("id")
    var id : Int? = 0,
    @SerializedName("is_stuff")
    var is_staff: Boolean? = false
)