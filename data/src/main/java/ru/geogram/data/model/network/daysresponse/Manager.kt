package ru.geogram.data.model.network.daysresponse

import com.google.gson.annotations.SerializedName

data class Manager(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val first_name: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_name")
    val last_name: String,
    @SerializedName("role")
    val role: String
)