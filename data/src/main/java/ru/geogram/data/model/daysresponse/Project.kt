package ru.geogram.data.model.daysresponse

import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_archived")
    val is_archived: Boolean,
    @SerializedName("is_commercial")
    val is_commercial: Boolean,
    @SerializedName("managers")
    val managers: List<Manager>,
    @SerializedName("name")
    val name: String
)