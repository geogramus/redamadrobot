package ru.geogram.data.model.network.daysresponse

import com.google.gson.annotations.SerializedName

data class DaysResponse(
    @SerializedName("data")
    val data: DataForDayResponse
)