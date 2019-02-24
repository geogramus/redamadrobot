package ru.geogram.data.model.daysresponse

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("text")
    val text: String
)