package ru.geogram.data.model.daysresponse

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("date")
    val date: String,
    @SerializedName("is_working")
    val is_working: Boolean,
    @SerializedName("logged_time_records")
    val logged_time_records: List<LoggedTimeRecord>
)